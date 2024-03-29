package com.rongyuan.mingyida.module.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseFragment;
import com.rongyuan.mingyida.model.ClassifyBeans;
import com.rongyuan.mingyida.model.HomeAllModel;
import com.rongyuan.mingyida.model.PictureModel;
import com.rongyuan.mingyida.module.classify.ClassifyActivity;
import com.rongyuan.mingyida.module.goods.AllGoodsActivity;
import com.rongyuan.mingyida.module.goods.goodsdetails.GoodsDetailsActivity;
import com.rongyuan.mingyida.module.servingproduct.AllservicesActivity;
import com.rongyuan.mingyida.module.servingproduct.servedetails.ServeDetailsActivity;
import com.rongyuan.mingyida.utils.GlideImageLoader;
import com.rongyuan.mingyida.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class HomeFragment extends BaseFragment implements HomeContract.IHomeView, OnBannerListener {

    @BindView(R.id.tv_home_info)
    TextView tvHomeInfo;
    @BindView(R.id.home_banner)
    Banner mBanner;

    @BindView(R.id.ed_home_search)
    TextView edHomeSearch;
    @BindView(R.id.home_recycler_classify)
    RecyclerView homeRecyclerClassify;
    @BindView(R.id.home_recycler_hot)
    RecyclerView homeRecyclerHot;
    @BindView(R.id.home_recycler_all)
    RecyclerView homeRecyclerAll;
    Unbinder unbinder;

    private HomePresenter mHomePresenter;
    ClassifyAdapter mClassifyAdapter;
    HotAdapter mHotAdapter;
    AllAdapter mAllAdapter;

    //
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mHomePresenter = new HomePresenter(this);
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        mBanner.setOnBannerListener(this);


        mHomePresenter.subscribe();
    }


    @Override
    public void showBannerFail(String failMessage) {
        ToastUtils.showError(getContext(), failMessage);
    }

    @Override
    public void setBanner(List<String> imgUrls) {
        mBanner.setImages(imgUrls).setImageLoader(new GlideImageLoader()).start();
    }

    @Override
    public void setRecyclerClassify(List<ClassifyBeans> itemdata) {
        mClassifyAdapter = new ClassifyAdapter(itemdata);
        mClassifyAdapter.openLoadAnimation();
        mClassifyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ToastUtils.showSuccess(getContext(), "点击" + position);
                switch (position) {
                    case 0:
                        Intent intentA  = new Intent(getContext(), AllservicesActivity.class);
                        intentA.putExtra("toolbarTitle","洗车");
                        startActivity(intentA);
                        break;
                    case 1:
                        Intent intentB  = new Intent(getContext(), AllservicesActivity.class);
                        intentB.putExtra("toolbarTitle","保养");
                        startActivity(intentB);
                        break;
                    case 2:
                        Intent intentC  = new Intent(getContext(), AllservicesActivity.class);
                        intentC.putExtra("toolbarTitle","改装");
                        startActivity(intentC);
                        break;
                    case 3:
                        Intent intentD  = new Intent(getContext(), AllGoodsActivity.class);
                        intentD.putExtra("toolbarTitle","汽车用品");
                        startActivity(intentD);
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), ClassifyActivity.class));
                        break;
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 5);
        homeRecyclerClassify.setLayoutManager(linearLayoutManager);
        homeRecyclerClassify.setNestedScrollingEnabled(false);
        homeRecyclerClassify.setAdapter(mClassifyAdapter);
    }

    @Override
    public void setRecyclerHot(List<PictureModel> itemdata) {
        mHotAdapter = new HotAdapter(itemdata);
        mHotAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mHotAdapter.isFirstOnly(false);
        mHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), GoodsDetailsActivity.class));
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeRecyclerHot.setLayoutManager(linearLayoutManager);
        homeRecyclerHot.setAdapter(mHotAdapter);
    }

    @Override
    public void setRecyclerall(List<HomeAllModel> itemdata) {
        mAllAdapter = new AllAdapter(itemdata);
        mAllAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        mAllAdapter.isFirstOnly(true);
        mAllAdapter.setOnItemChildClickListener(
                new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.layout_home_one:
                                ToastUtils.showInfo(getContext(), "one" + position);
                                break;
                            case R.id.layout_home_two:
                                ToastUtils.showInfo(getContext(), "two" + position);
                                break;
                            case R.id.layout_home_three:
                                ToastUtils.showInfo(getContext(), "three" + position);
                                break;
                        }
                    }
                }
        );
        homeRecyclerAll.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecyclerAll.setNestedScrollingEnabled(false);
        homeRecyclerAll.setAdapter(mAllAdapter);
    }

    @Override
    public void OnBannerClick(int position) {
        PictureModel model = mHomePresenter.getBannerModel().get(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHomePresenter != null) {
            mHomePresenter.unSubscribe();
        }
        unbinder.unbind();
    }

    @OnClick({R.id.ed_home_search, R.id.tv_home_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_home_search:
                break;
            case R.id.tv_home_info:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
