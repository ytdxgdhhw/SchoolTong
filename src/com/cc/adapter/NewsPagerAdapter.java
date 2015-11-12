package com.cc.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cc.entity.Channel;

public class NewsPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragments;
	private List<Channel> channels;
	public NewsPagerAdapter(FragmentManager fm, List<Fragment> fragments,
			List<Channel> channels) {
		super(fm);
		this.fragments = fragments;
		this.channels = channels;
	}
	public NewsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return this.fragments.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return this.channels.get(position).getcTitle();
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.fragments.size();
	}

}
