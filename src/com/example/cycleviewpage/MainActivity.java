package com.example.cycleviewpage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ViewPager vp;
	private int[] ids = { R.drawable.a, R.drawable.b, R.drawable.c,
			R.drawable.d };
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		vp = (ViewPager) findViewById(R.id.vp);
		vp.setAdapter(new Adapter());
	}

	private class Adapter extends PagerAdapter {

		@Override
		public int getCount() {
			//在图片资源的前后各添加一张图片
			return ids.length ;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(context);
			image.setBackgroundResource(ids[position]);
			container.addView(image);
			return image;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
