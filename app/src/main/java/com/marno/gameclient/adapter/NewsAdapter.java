package com.marno.gameclient.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.marno.gameclient.R;
import com.marno.gameclient.adapter.common.RecyclerAdapter;
import com.marno.gameclient.adapter.common.RecyclerAdapterHelper;
import com.marno.gameclient.data.models.ImageEntity;
import com.marno.gameclient.data.models.NewsEntity;
import com.marno.gameclient.data.retrofit.RetrofitClient;
import com.marno.gameclient.module.web.WebViewActivity;
import com.marno.gameclient.utils.ActivityUtil;

import java.util.List;

/**
 * Created by marno on 2016/7/24/14:34.
 */
public class NewsAdapter extends RecyclerAdapter<NewsEntity> {

    public static final int TMPELATE_NO_PIC = 1;//没有照片
    public static final int TMPELATE_ONE_BIG_PIC = 3;//一张大图
    public static final int TMPELATE_THREE_SMALL_PIC = 4;//三张小图
    public static final int TMPELATE_ONE_SAMLL_PIC = 5;//一张小图
    private Context mContext;

    public NewsAdapter(Context context) {
        this(context, R.layout.item_news_template5, R.layout.item_news_template4,
                R.layout.item_news_template3, R.layout.item_news_template1);
    }

    public NewsAdapter(Context context, @NonNull int... layoutResIds) {
        super(context, layoutResIds);
        this.mContext = context;
    }

    @Override
    protected void convert(RecyclerAdapterHelper helper, NewsEntity item) {
        int type = getItemViewType(helper.getItemViewType());
        List<ImageEntity> img = item.getImg();
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_pubtime, item.getCreate_time())
                .setText(R.id.tv_sourceName, "来源：" + item.getSource_name())
                .getItemView().setOnClickListener(v ->
                ActivityUtil.start(mContext, WebViewActivity.class, RetrofitClient.URL_BASE_NEWS + item.getUrl()));
        if (!img.isEmpty()) {
            if (type == TMPELATE_ONE_BIG_PIC || type == TMPELATE_ONE_SAMLL_PIC) {
                helper.setImageUrl(R.id.iv_thumb, img.get(0).getUrl(), false);
            } else if (type == TMPELATE_THREE_SMALL_PIC && img.size() == 3) {
                helper.setImageUrl(R.id.iv_thumb, img.get(0).getUrl(), false)
                        .setImageUrl(R.id.iv_thumb2, img.get(1).getUrl(), false)
                        .setImageUrl(R.id.iv_thumb3, img.get(2).getUrl(), false);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        NewsEntity newsEntity = get(position);
        String templateId = newsEntity.getTemplate_id().trim();
        if (TextUtils.isEmpty(templateId)) templateId = "1";
        return Integer.parseInt(templateId);
    }


    @Override
    public int getLayoutResId(int viewType) {
        if (viewType == TMPELATE_ONE_BIG_PIC) {
            return R.layout.item_news_template3;
        } else if (viewType == TMPELATE_THREE_SMALL_PIC) {
            return R.layout.item_news_template4;
        } else if (viewType == TMPELATE_ONE_SAMLL_PIC) {
            return R.layout.item_news_template5;
        } else {
            return R.layout.item_news_template1;
        }
    }
}
