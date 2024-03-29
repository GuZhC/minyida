package com.rongyuan.mingyida.module.home;

import com.rongyuan.mingyida.base.BasePresenter;
import com.rongyuan.mingyida.base.BaseView;
import com.rongyuan.mingyida.model.ClassifyBeans;
import com.rongyuan.mingyida.model.HomeAllModel;
import com.rongyuan.mingyida.model.PictureModel;

import java.util.List;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public interface HomeContract {
    interface IHomeView extends BaseView {
        void showBannerFail(String failMessage);

        void setBanner(List<String> imgUrls);

        void setRecyclerClassify(List<ClassifyBeans> itemdata);

        void setRecyclerHot(List<PictureModel> itemdata);

        void setRecyclerall(List<HomeAllModel> itemdata);
    }

    interface IHomePresenter extends BasePresenter{

        void getRecyclerDataHot();

        void getRecyclerDataClassify();

        void getRecyclerDataAll();

        void getBannerData();
    }
}
