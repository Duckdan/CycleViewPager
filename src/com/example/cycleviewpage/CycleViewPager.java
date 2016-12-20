package com.example.cycleviewpage;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CycleViewPager extends ViewPager {
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				int currentItem = getCurrentItem();
				currentItem += 1;
				setCurrentItem(currentItem);
				handler.sendEmptyMessageDelayed(0, 5000);
			}
		};
	};

	public CycleViewPager(Context context) {
		super(context);
	}

	public CycleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		InnerPageChangeListener innerListener = new InnerPageChangeListener(
				listener);
		super.setOnPageChangeListener(innerListener);
	}

	@Override
	public void setAdapter(PagerAdapter adapter) {
		InnerPagerAdapter innerAdapter = new InnerPagerAdapter(adapter);
		super.setAdapter(innerAdapter);
		setOnPageChangeListener(null);
		setCurrentItem(1);
		startScroll();
	}

	private void startScroll() {
		handler.sendEmptyMessageDelayed(0, 2000);
	}

	private void stopScroll() {
		handler.removeMessages(0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			stopScroll();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			startScroll();
			break;
		}
		return super.onTouchEvent(ev);
	}

	private class InnerPageChangeListener implements OnPageChangeListener {

		private OnPageChangeListener listener;
		private int position;

		public InnerPageChangeListener(OnPageChangeListener listener) {
			this.listener = listener;

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			if (listener != null) {
				listener.onPageScrolled(position, positionOffset,
						positionOffsetPixels);
			}
		}

		@Override
		public void onPageSelected(int position) {
			this.position = position;
			if (listener != null) {
				listener.onPageSelected(position);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				if (position == getAdapter().getCount() - 1) {
					// 当图片滑动到最后一个位置的时候，将位置改变为A图片
					position = 1;
				} else if (position == 0) {
					// 当图片滑动到第一个位置的时候，将位置改变为D图片
					position = getAdapter().getCount() - 2;
				}
				setCurrentItem(position, false);
			}
			if (listener != null) {
				listener.onPageScrollStateChanged(state);
			}
		}

	}

	private class InnerPagerAdapter extends PagerAdapter {

		private PagerAdapter adapter;

		public InnerPagerAdapter(PagerAdapter adapter) {
			this.adapter = adapter;
		}

		@Override
		public int getCount() {
			return adapter.getCount() + 2;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			if (position == getCount() - 1) {
				// 当图片滑动到最后一个位置的时候，将位置改变为A图片
				position = 0;
			} else if (position == 0) {
				// 当图片滑动到第一个位置的时候，将位置改变为D图片
				position = getCount() - 3;
			} else {
				position = position - 1;
			}
			return adapter.instantiateItem(container, position);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return adapter.isViewFromObject(view, object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			adapter.destroyItem(container, position, object);
		}
	}

}
