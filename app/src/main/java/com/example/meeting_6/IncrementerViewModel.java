package com.example.meeting_6;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IncrementerViewModel extends ViewModel {
    private MutableLiveData<Boolean> isStarted = new MutableLiveData<Boolean>(false);
    private MutableLiveData<Integer> number;
    private Thread thread;

    public LiveData<Integer> getValue() {
        if (number == null){
            number = new MutableLiveData<Integer>(0);
        }
        return number;
    }

    public void execute() {

        if(!isStarted.getValue()) {
            number.postValue(0);
            isStarted.postValue(true);
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int i = number.getValue(); i <= 100; i++){

                        try{
                            number.postValue(i);
                            Thread.sleep(300);
                        }catch (InterruptedException e){
                            e.getMessage();
                            thread.interrupt();
                        }
                    }
                    if(thread.isInterrupted()) {
                        number.postValue(0);
                    }
                }
            };
            thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void stop() {
        thread.interrupt();
        isStarted.postValue(false);
        number.postValue(0);
    }
}
