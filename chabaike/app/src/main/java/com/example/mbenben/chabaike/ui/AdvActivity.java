package com.example.mbenben.chabaike.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mbenben.chabaike.R;

import java.util.ArrayList;

/**
 * Created by MBENBEN on 2016/6/20.
 */
public class AdvActivity extends Activity {
    private ViewPager viewPager;
    private ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    private int[] images ={R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};
    private int preposition=0;
    private LinearLayout adv_ll;
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

                Intent intent = new Intent();
                intent.setClass(AdvActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv);
        adv_ll = (LinearLayout) findViewById(R.id.adv_ll);
        initView();
        initViewPager();

    }



    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.adv_vp);
        AdvAdapter advAdapter=new AdvAdapter();
        viewPager.setAdapter(advAdapter);
        viewPager.addOnPageChangeListener(advAdapter);
    }



    private void initView() {
        //初始化Linearlayout
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.adv_ll);
        //设置图片的一些属性
        LinearLayout.LayoutParams iv_layoout_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        //设置圆点的一些属性
        LinearLayout.LayoutParams page_layoout_params = new LinearLayout.LayoutParams(10,10);
        page_layoout_params.leftMargin=10;
        //动态加圆点
        View view_page = null;

        for(int i=0;i<images.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(images[i]);
            imageView.setLayoutParams(iv_layoout_params);
            //设置图片的显示方式，不按比例缩放图片，目标是把图片塞满整个View
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //设置完图片后加入到集合中
            imageViews.add(imageView);
            view_page = new View(this);
            if(i==0){
                view_page.setBackgroundResource(R.drawable.page_now);
            }else{
                view_page.setBackgroundResource(R.drawable.page);
            }
            view_page.setLayoutParams(page_layoout_params);
            adv_ll.addView(view_page);

        }
    }



    class AdvAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //View view = imageViews.get(position);
            container.addView(imageViews.get(position));

            return imageViews.get(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        //滑动到最后一页时 跳转到主界面
        @Override
        public void onPageSelected(int position) {
            adv_ll.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            adv_ll.getChildAt(preposition).setBackgroundResource(R.drawable.page);
            preposition = position;



            if(position==(images.length-1)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //在这里设置ShareParences的值，让它下次不进入这个Activity
    @Override
    protected void onResume() {
        super.onResume();
    }
}
