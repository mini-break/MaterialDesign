package com.fanyafeng.materialdesign.fragment;

import android.content.Context;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.fanyafeng.materialdesign.Constant.MaterialDesignConstant;
import com.fanyafeng.materialdesign.R;
import com.fanyafeng.materialdesign.adapter.RVAdapter;
import com.fanyafeng.materialdesign.view.StickyNavLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabLayoutFragment extends Fragment {
    private final static String TAG = "TabLayoutFragment";

    private static final String ARG_PARAM1 = "flag";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private RecyclerView.LayoutManager layoutManager;

    private RVAdapter rvAdapter;
    private List<String> stringList;

    //        private XRefreshView xrvTabViewRefresh;
    private RecyclerView rvTabView;
    private GestureDetector mGestureDetector;
    private final int SCROLL = 0;
    private final int FLING = 1;

    public TabLayoutCallBack tabLayoutCallBack;

    public void setTabLayoutCallBack(TabLayoutCallBack tabLayoutCallBack) {
        this.tabLayoutCallBack = tabLayoutCallBack;
    }

    public interface TabLayoutCallBack {
        void callBack(String tabPosition, int totalSize, int firstPosition, int lastPosition);
    }


    public TabLayoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabLayoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabLayoutFragment newInstance(String param1, String param2) {
        TabLayoutFragment fragment = new TabLayoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
//        xrvTabViewRefresh = (XRefreshView) view.findViewById(R.id.xrvTabViewRefresh);
//        xrvTabViewRefresh.setPullRefreshEnable(false);
//        xrvTabViewRefresh.setPullLoadEnable(false);
//        xrvTabViewRefresh.setMoveForHorizontal(true);
//        xrvTabViewRefresh.setMoveHeadWhenDisablePullRefresh(false);

        mGestureDetector = new GestureDetector(getActivity(), new RecycleViewGestureListener());

        rvTabView = (RecyclerView) view.findViewById(R.id.rvTabView);
        rvTabView.setHasFixedSize(true);
        switch (mParam1) {
            case "0":
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case "1":
                layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                break;
            case "2":
                layoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
                break;
            case "3":
                layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case "4":
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
        }
        rvTabView.setLayoutManager(layoutManager);
        stringList = new ArrayList<>();
        stringList = Arrays.asList(MaterialDesignConstant.imageList);
//        stringList = Arrays.asList("http://img1.imgtn.bdimg.com/it/u=753665811,2183009962&fm=21&gp=0.jpg");
        rvAdapter = new RVAdapter(getActivity(), stringList);
        rvTabView.setAdapter(rvAdapter);
    }

    int rvState = -1;

    int currentY;
    int lastY;

    private void initData() {
        rvTabView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        currentY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        lastY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:

                        if (rvTabView.getLayoutManager() instanceof GridLayoutManager) {
                            if (((GridLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition() <= 0) {
                                if (lastY - currentY > 0) {
                                    if (tabLayoutCallBack != null) {
                                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, ((GridLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition(), ((GridLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition());
                                    }
                                }
                            }
                            if (((GridLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition() == MaterialDesignConstant.imageList.length - 1) {
                                if (lastY - currentY < 0) {
                                    Log.d(TAG, "y-currentY:小于0");
                                    if (tabLayoutCallBack != null) {
                                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, ((GridLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition(), ((GridLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition());
                                    }
                                }
                            }
                        } else if (rvTabView.getLayoutManager() instanceof LinearLayoutManager) {
                            if (((LinearLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition() <= 0) {
                                if (lastY - currentY > 0) {
                                    if (tabLayoutCallBack != null) {
                                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, ((LinearLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition(), ((LinearLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition());
                                    }
                                }
                            }
                            if (((LinearLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition() == MaterialDesignConstant.imageList.length - 1) {
                                if (lastY - currentY < 0) {
                                    Log.d(TAG, "y-currentY:小于0");
                                    if (tabLayoutCallBack != null) {
                                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, ((LinearLayoutManager) (rvTabView.getLayoutManager())).findFirstCompletelyVisibleItemPosition(), ((LinearLayoutManager) (rvTabView.getLayoutManager())).findLastCompletelyVisibleItemPosition());
                                    }
                                }
                            }
                        }


                        break;
                }
                return false;
            }
        });


        rvTabView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when RecyclerView's scroll state changes.
             * fuck!!! do not tell me clearly begin
             * The RecyclerView whose scroll state has changed.
             * The updated scroll state. One of {RecyclerView.SCROLL_STATE_IDLE},
             *                     {RecyclerView.SCROLL_STATE_DRAGGING} or {RecyclerView.SCROLL_STATE_SETTLING}.
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "recyclerview state:" + newState);//when recyclerView stop scroll the newState==0
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    ((GridLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                    ((GridLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
//                    if (tabLayoutCallBack != null) {
//                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, firstPosition, lastPosition);
//                    }
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    ((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastCompletelyVisibleItemPosition();
                    ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstCompletelyVisibleItemPosition();
//                    if (tabLayoutCallBack != null) {
//                        tabLayoutCallBack.callBack(mParam1, MaterialDesignConstant.imageList.length, firstPosition, lastPosition);
//                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "onScrolled dx:" + dx + ",dy:" + dy);
            }
        });
    }

    class RecycleViewGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent ev) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "GestureDetector: onScroll,distanceY:" + distanceY);
            rvState = SCROLL;
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            Log.d(TAG, "GestureDetector: onFling");
            rvState = FLING;
            return true;
        }

        @Override
        public boolean onDown(MotionEvent ev) {
            return true;
        }
    }

}
