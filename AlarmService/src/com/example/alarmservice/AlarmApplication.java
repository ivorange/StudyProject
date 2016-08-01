package com.example.alarmservice;

import java.util.List;

import com.google.inject.Module;

import android.app.Application;
import roboguice.application.RoboApplication;

public class AlarmApplication extends RoboApplication{

	@Override
	protected void addApplicationModules(List<Module> modules) {
		// TODO Auto-generated method stub
		super.addApplicationModules(modules);
	}
	
}
