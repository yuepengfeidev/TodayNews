package com.example.a79875.todaynews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.helper.OnDragVHListener;
import com.example.a79875.todaynews.helper.OnItemMoveListener;

import java.util.List;

// 编辑频道 recyclerview 的适配器
public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;

    // 我的频道之前的header数量 该demo中 即标题部分 为1
    private static final int COUNT_PRE_MY_HEADER = 1;
    // 其他频道之前的header数量 该demo中 该标题部分 为 COUT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

    private static final long ANIM_TIME = 360L;

    // touch 点击开始时间
    private long startTime;
    // touch 间隔时间 用于分辨是否是"点击"
    private static final long SPACE_TIME = 100;

    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;

    // 是否是编辑模式
    private boolean isEditMode;

    private List<String> mMyChannelItems, mOtherChannelItem;

    // 我的频道点击事件
    private OnMyChannelItemClickListener onMyChannelItemClickListener;

    UpdateData updateData;// 更新数据的接口

    public ChannelAdapter(Context context, ItemTouchHelper helper, List<String> mMyChannelItems,
                          List<String> mOtherChannelItem) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.mMyChannelItems = mMyChannelItems;
        this.mOtherChannelItem = mOtherChannelItem;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) { // 我的频道，标题部分
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + 1) {// 其他频道，标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + 1) {
            return TYPE_MY;
        } else {
            return TYPE_OTHER;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, int viewType) {
        final View view;
        switch (viewType) {
            case TYPE_MY_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_my_channel_header, viewGroup, false);
                final MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);
                holder.tvBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            startEditMode((RecyclerView) viewGroup);
                            holder.tvBtnEdit.setText("完成");
                        } else {
                            cancelEditMode((RecyclerView) viewGroup);
                            holder.tvBtnEdit.setText("编辑");
                        }
                    }
                });
                return holder;

            case TYPE_MY:
                view = mInflater.inflate(R.layout.category_item_drag, viewGroup, false);
                final MyViewHolder myHolder = new MyViewHolder(view);

                myHolder.ivDelete.setOnClickListener(new View.OnClickListener() {// 删除的点击事件 加动画效果
                    @Override
                    public void onClick(final View v) {
                        int position = myHolder.getAdapterPosition();
                        if (isEditMode) {
                            RecyclerView recyclerView = ((RecyclerView) viewGroup);
                                View targetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size()
                                    + COUNT_PRE_OTHER_HEADER);
                            View currentView = recyclerView.getLayoutManager().findViewByPosition(position);
                            // 如果targetView不再屏幕里，则indexOfChild为-1 此时不需要添加动画，因此此时notifyItemMoved自带一向目标移动的动画
                            // 如果在屏幕里，则添加一个位移动画
                            if (recyclerView.indexOfChild(targetView) >= 0) {
                                int targetX, targetY;

                                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();

                                // 移动后 高度将变化 （我的频道Grid 最后一个item在新的第一行第一个)
                                if ((mMyChannelItems.size() - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                    View preTargetView = recyclerView.getLayoutManager().findViewByPosition(mMyChannelItems.size()
                                            + COUNT_PRE_OTHER_HEADER - 1);
                                    targetX = preTargetView.getLeft();
                                    targetY = preTargetView.getTop();
                                } else {
                                    targetX = targetView.getLeft();
                                    targetY = targetView.getTop();
                                }

                                moveMyToOther(myHolder);
                                startAnimation(recyclerView, currentView, targetX, targetY);
                            } else {// 如果targetview在屏幕之外，则只是移除item，不添加动画
                                moveMyToOther(myHolder);
                            }
                        } else {
                            onMyChannelItemClickListener.onItemClick(v, position - COUNT_PRE_MY_HEADER);
                        }
                    }
                });

                myHolder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!isEditMode) {// 长按显示完成按钮
                            RecyclerView recyclerView = ((RecyclerView) viewGroup);
                            startEditMode(recyclerView);

                            // header 按钮文字改成 “完成”
                            View headerView = recyclerView.getChildAt(0);// 获取我的频道标题的view
                            if (headerView == recyclerView.getLayoutManager().findViewByPosition(0)) {
                                TextView tvBtnEdit = headerView.findViewById(R.id.tv_btn_edit);
                                tvBtnEdit.setText("完成");
                            }
                        }

                        mItemTouchHelper.startDrag(myHolder);// 打开我的频道的为可拖动item
                        return true;
                    }
                });

                myHolder.itemLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (isEditMode) {
                            switch (MotionEventCompat.getActionMasked(event)) {
                                case MotionEvent.ACTION_DOWN:
                                    startTime = System.currentTimeMillis();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                        mItemTouchHelper.startDrag(myHolder);
                                    }
                                    break;
                                case MotionEvent.ACTION_UP:
                                    startTime = 0;
                                    break;
                            }
                        }
                        return false;
                    }
                });
                return myHolder;

            case TYPE_OTHER_CHANNEL_HEADER:
                view = mInflater.inflate(R.layout.item_other_channel_header, viewGroup, false);
                return new RecyclerView.ViewHolder(view) {
                };

            case TYPE_OTHER:
                view = mInflater.inflate(R.layout.category_item_default, viewGroup, false);
                final OtherViewHolder otherHolder = new OtherViewHolder(view);
                otherHolder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecyclerView recyclerView = ((RecyclerView) viewGroup);
                        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                        int currentPiosition = otherHolder.getAdapterPosition();
                        // 如果RecyclerView滑动到底部,移动的目标位置的y轴 - height
                        View currentView = manager.findViewByPosition(currentPiosition);
                        // 目标位置的前一个item  即当前MyChannel的最后一个
                        View preTargetView = manager.findViewByPosition(mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);

                        // 如果targetView不在屏幕内,则为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                        // 如果在屏幕内,则添加一个位移动画
                        if (recyclerView.indexOfChild(preTargetView) >= 0) {
                            int targetX = preTargetView.getLeft();
                            int targetY = preTargetView.getTop();

                            int targetPosition = mMyChannelItems.size() - 1 + COUNT_PRE_OTHER_HEADER;

                            GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
                            int spanCount = gridLayoutManager.getSpanCount();
                            // target 在最后一行第一个
                            if ((targetPosition - COUNT_PRE_MY_HEADER) % spanCount == 0) {
                                View targetView = manager.findViewByPosition(targetPosition);
                                targetX = targetView.getLeft();
                                targetY = targetView.getTop();
                            } else {
                                targetX += preTargetView.getWidth();

                                // 最后一个item可见
                                if (gridLayoutManager.findLastVisibleItemPosition() == getItemCount() - 1) {
                                    // 最后的item在最后一行第一个位置
                                    if ((getItemCount() - 1 - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount == 0) {
                                        // RecyclerView实际高度 > 屏幕高度 && RecyclerView实际高度 < 屏幕高度 + item.height
                                        int firstVisiblePostion = gridLayoutManager.findFirstVisibleItemPosition();
                                        if (firstVisiblePostion == 0) {
                                            // FirstCompletelyVisibleItemPosition == 0 即 内容不满一屏幕 , targetY值不需要变化
                                            // // FirstCompletelyVisibleItemPosition != 0 即 内容满一屏幕 并且 可滑动 , targetY值 + firstItem.getTop
                                            if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                                                int offset = (-recyclerView.getChildAt(0).getTop()) - recyclerView.getPaddingTop();
                                                targetY += offset;
                                            }
                                        } else { // 在这种情况下 并且 RecyclerView高度变化时(即可见第一个item的 position != 0),
                                            // 移动后, targetY值  + 一个item的高度
                                            targetY += preTargetView.getHeight();
                                        }
                                    }
                                } else {
                                    System.out.println("current--No");
                                }
                            }

                            // 如果当前位置是otherChannel可见的最后一个
                            // 并且 当前位置不在grid的第一个位置
                            // 并且 目标位置不在grid的第一个位置

                            // 则 需要延迟250秒 notifyItemMove , 这是因为这种情况 , 并不触发ItemAnimator , 会直接刷新界面
                            // 导致我们的位移动画刚开始,就已经notify完毕,引起不同步问题
                            if (currentPiosition == gridLayoutManager.findLastVisibleItemPosition()
                                    && (currentPiosition - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER) % spanCount != 0
                                    && (targetPosition - COUNT_PRE_MY_HEADER) % spanCount != 0) {
                                moveOtherToMyWithDelay(otherHolder);
                            } else {
                                moveOtherToMy(otherHolder);
                            }
                            startAnimation(recyclerView, currentView, targetX, targetY);

                        } else {
                            moveOtherToMy(otherHolder);
                        }
                    }
                });
                return otherHolder;
        }

        return null;
    }

    // 其他频道移动到我的频道伴随延迟,避免提前notify
    private void moveOtherToMyWithDelay(OtherViewHolder otherViewHolder) {
        final int position = processItemRemoveAdd(otherViewHolder);
        if (position == -1) {
            return;
        }
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
            }
        }, ANIM_TIME);
    }

    private Handler delayHandler = new Handler();

    private int processItemRemoveAdd(OtherViewHolder otherViewHolder) {
        int position = otherViewHolder.getAdapterPosition();

        int startPosition = position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER;
        if (startPosition > mOtherChannelItem.size() - 1) {
            return -1;
        }
        String item = mOtherChannelItem.get(startPosition);
        mOtherChannelItem.remove(startPosition);
        mMyChannelItems.add(item);
        return position;
    }

    // 推荐频道移动到我的频道
    private void moveOtherToMy(OtherViewHolder otherViewHolder) {
        int position = processItemRemoveAdd(otherViewHolder);
        if (position == -1) {
            return;
        }
        notifyItemMoved(position, mMyChannelItems.size() - 1 + COUNT_PRE_MY_HEADER);
    }

    // 开始增删动画
    private void startAnimation(RecyclerView recyclerView, final View currentView, float targetX, float targetY) {
        final ViewGroup viewGroup = (ViewGroup) recyclerView.getParent();
        final ImageView mirrorView = addMirrorView(viewGroup, recyclerView, currentView);

        // 得到设置好的动画
        Animation animation = getTranslateAnimator(
                targetX - currentView.getLeft(),
                targetY - currentView.getTop());// 值为 横纵坐标 需要移动距离

        currentView.setVisibility(View.INVISIBLE);// 设置当前item为不可见
        mirrorView.startAnimation(animation);// 开始动画效果

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewGroup.removeView(mirrorView);// 移除该镜像 view
                if (currentView.getVisibility() == View.INVISIBLE) {// 动画效果结束后，显示该item
                    currentView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 获取位移动画
     */
    private TranslateAnimation getTranslateAnimator(float targetX, float targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetY);
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }


    // 添加需要移动的镜像文件
    private ImageView addMirrorView(ViewGroup viewGroup, RecyclerView recyclerView, View currentView) {
        /**
         * 我们获取cache首先要通过setDrawingCacheEnable方法开启cache，然后调用getDrawingCache方法就可以获得view的cache照片了
         * buildDrawingCache方法可以不调用，因为调用getDrawingCache方法时，若是cache没有建立，系统会自动调用buildDrawingCache
         *方法生成cache。若想更新cache，必须要调用destoryDrawingCache方法把旧的cache销毁，才能建立新的。
         * 当调用setDrawingCacheEable方法设置为false，系统也会自动把原来的cache销毁。
         */
        currentView.destroyDrawingCache();
        currentView.setDrawingCacheEnabled(true);
        final ImageView mirrorView = new ImageView(recyclerView.getContext());
        Bitmap bitmap = Bitmap.createBitmap(currentView.getDrawingCache());// 获取cache中的图片
        mirrorView.setImageBitmap(bitmap);// 设置cache中取出的图片
        currentView.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        currentView.getLocationOnScreen(locations);//获取当前item距屏幕的距离 loacation[1]离屏幕左侧的距离 location[2]离屏幕上边的距离
        int[] parentLoacation = new int[2];
        recyclerView.getLocationOnScreen(parentLoacation);// 获取父布局recyclerview距里屏幕的距离
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parentLoacation[1], 0, 0);// 设置页边距的属性
        viewGroup.addView(mirrorView, params);

        return mirrorView;
    }


    // 我的频道移到推荐频道
    private void moveMyToOther(MyViewHolder myHolder) {
        int position = myHolder.getAdapterPosition();

        int startPosition = position - COUNT_PRE_MY_HEADER;
        if (startPosition > mMyChannelItems.size() - 1) {
            return;
        }

        String item = mMyChannelItems.get(startPosition);
        mMyChannelItems.remove(startPosition);// 我的频道移除该item
        mOtherChannelItem.add(0, item);// 推荐频道添加该item

        notifyItemMoved(position, mMyChannelItems.size() + COUNT_PRE_OTHER_HEADER);
    }

    // 完成编辑模式
    private void cancelEditMode(RecyclerView viewGroup) {
        isEditMode = false;

        int visibleChildCount = viewGroup.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = viewGroup.getChildAt(i);
            ImageView ivEdit = view.findViewById(R.id.iv_category_item_delete);
            if (ivEdit != null) {
                ivEdit.setVisibility(View.GONE);
            }
        }
    }

    // 开启编辑模式
    private void startEditMode(RecyclerView parent) {
        isEditMode = true;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView ivEidt = (ImageView) view.findViewById(R.id.iv_category_item_delete);
            if (ivEidt != null) {
                ivEidt.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

            myViewHolder.tvChannelName.setText(mMyChannelItems.get(i - COUNT_PRE_MY_HEADER));
            if (isEditMode) {
                myViewHolder.ivDelete.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.ivDelete.setVisibility(View.INVISIBLE);
            }
        } else if (viewHolder instanceof OtherViewHolder) {
            ((OtherViewHolder) viewHolder).tvChannelName.setText("+" + mOtherChannelItem.get(i - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER));
        } else if (viewHolder instanceof MyChannelHeaderViewHolder) {
            MyChannelHeaderViewHolder headHolder = (MyChannelHeaderViewHolder) viewHolder;
            if (isEditMode) {
                headHolder.tvBtnEdit.setText("完成");
            } else {
                headHolder.tvBtnEdit.setText("编辑");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mMyChannelItems.size() + mOtherChannelItem.size() + COUNT_PRE_OTHER_HEADER;
    }

    // item 排序移动动画
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }


    public interface OnMyChannelItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnMyChannelItemClickListener(OnMyChannelItemClickListener onMyChannelItemClickListener) {
        this.onMyChannelItemClickListener = onMyChannelItemClickListener;
    }

    // 我的频道 标题部分
    private class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBtnEdit;

        public MyChannelHeaderViewHolder(View view) {
            super(view);
            tvBtnEdit = view.findViewById(R.id.tv_btn_edit);
        }
    }

    // 我的频道
    private class MyViewHolder extends RecyclerView.ViewHolder implements OnDragVHListener {
        private TextView tvChannelName;
        private ImageView ivDelete;
        private LinearLayout itemLayout;

        public MyViewHolder(View view) {
            super(view);
            tvChannelName = view.findViewById(R.id.tv_category_item_drag_name);
            ivDelete = view.findViewById(R.id.iv_category_item_delete);
            itemLayout = view.findViewById(R.id.layout_item);
        }

        // item 被选中时
        @Override
        public void onItemSelected() {
            itemLayout.setBackgroundResource(R.drawable.bg_channel_p);
        }

        // item 取消选中时
        @Override
        public void onItemFinish() {
            itemLayout.setBackgroundResource(R.drawable.bg_channel);
        }
    }

    // 推荐的其他频道
    private class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChannelName;
        private LinearLayout layout;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChannelName = itemView.findViewById(R.id.tv_category_item_default_name);
            layout = itemView.findViewById(R.id.layout_item_default);
        }
    }

    public interface UpdateData{
        void update(List<String> myChannelList, List<String> otherChannelList);
    }

    public void setUpdateData(UpdateData updateData){
        this.updateData = updateData;

        data();
    }

    public void data(){
        updateData.update(mMyChannelItems,mOtherChannelItem);
    }
}
