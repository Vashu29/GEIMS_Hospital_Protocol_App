package com.example.geims_navigation_2.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import com.example.geims_navigation_2.Domain.CategoryModel;
import com.example.geims_navigation_2.Domain.DoctorsModel;
import com.example.geims_navigation_2.Repository.MainRepository;

public class MainViewModel extends ViewModel {
    private final MainRepository repository;

    public MainViewModel() {
        this.repository = new MainRepository();
    }
    public LiveData<List<CategoryModel>> loadCategory(){ return repository.loadCategory();}

    public LiveData<List<DoctorsModel>> loadDoctors(){
        return repository.loadDoctor();
    }
}














































