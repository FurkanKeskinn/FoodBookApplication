package com.example.foodbooksactivity.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.foodbooksactivity.model.Food
import com.example.foodbooksactivity.service.FoodAPIServices
import com.example.foodbooksactivity.service.FoodDatabase
import com.example.foodbooksactivity.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    val foods = MutableLiveData<List<Food>>()
    val foodErrorMessage = MutableLiveData<Boolean>()
    val foodsLoading = MutableLiveData<Boolean>()

    private var uptadeTime = 1 * 60 * 1000 * 1000 * 1000L  //dakikanın nano time dönüşüm hali

    private val foodApiServis = FoodAPIServices()
    private val disposable = CompositeDisposable() //RxJava

    private val customSharedPreferences = CustomSharedPreferences(getApplication())

    fun refreshData() {
        /*val banana = Food("Muz","100","10","5","1","www.test.com")
            val strawberry = Food("çilek","200","10","5","1","www.test.com")
            val apple = Food("elma","300","10","5","1","www.test.com")

            val foodList = arrayListOf<Food>(banana,strawberry,apple)
            foods.value = foodList
            foodErrorMessage.value = false
            foodsLoading.value = false*/

        val saveTime = customSharedPreferences.getTime()
        if (saveTime != null && saveTime != 0L && System.nanoTime() - saveTime<uptadeTime){

            //ise sqliteden verileri çek
            getDataSQLite()

        }else{
            getDataInternet()
        }

    }

    fun refreshFromInternet(){
        getDataInternet()
    }

    private fun getDataSQLite(){
        foodsLoading.value = true

        launch {
            val foodList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodList)
            Toast.makeText(getApplication(),"Besinleri Room'dan Aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataInternet() {
        foodsLoading.value = true

        disposable.addAll(
            foodApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>() {
                    override fun onSuccess(t: List<Food>) {
                        //başarılı olursa
                        hideSqlite(t)
                        Toast.makeText(getApplication(),"Besinleri İnternet'ten Aldık",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        //hata alırsak

                        foodErrorMessage.value = true
                        foodsLoading.value = false
                        e.printStackTrace()

                    }

                })


        )
    }

    private fun showFoods(foodsList: List<Food>) {
        foods.value = foodsList
        foodErrorMessage.value = false
        foodsLoading.value = false
    }

    private fun hideSqlite(foodList: List<Food>) {
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFood()
            val uuidList = dao.insertAll(*foodList.toTypedArray())  //buradaki * sembolü verilerin tek tek gelmesini sağlar
            var i = 0
            while (i < foodList.size) {
                foodList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            showFoods(foodList)

        }

        customSharedPreferences.savedTime(System.nanoTime())

    }
}