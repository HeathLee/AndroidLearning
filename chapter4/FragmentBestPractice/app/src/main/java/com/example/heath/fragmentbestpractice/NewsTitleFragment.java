package com.example.heath.fragmentbestpractice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heath on 15-10-15.
 */
public class NewsTitleFragment extends Fragment
        implements AdapterView.OnItemClickListener {
    private ListView newsTitleListView;
    private List<News> newsList;
    private NewsAdapter newsAdapter;
    private boolean isTwoPane;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        newsList = getNews();
        newsAdapter = new NewsAdapter(context, R.layout.news_item, newsList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container,
                false);
        newsTitleListView = (ListView) view.findViewById(R.id
                .news_title_list_view);
        newsTitleListView.setAdapter(newsAdapter);
        newsTitleListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setTitle("Succeed in College as a Learning Disabled Student");
        news.setContent("College freshmen will soon learn to live with a" +
                "roommate, adjust to a new social scene and survive" +
                "less-than-stellar dining hall food. Students with learning " +
                "disabilities will face these transitions while also " +
                "grappling with a few more hurdles.");
        News news1 = new News();
        news1.setTitle("Google Android exec poached by China's Xiaomi");
        news1.setContent("China's Xiaomi has poached a key Google " +
                "executive involved in the tech giant's Android phones," +
                "in a move seen as a coup for the rapidly growing Chinese " +
                "smartphone maker.");
        newsList.add(news);
        newsList.add(news1);
        return newsList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        News news = newsList.get(position);
        if (isTwoPane) {
            //双页模式
            NewsContentFragment newsContentFragment = (NewsContentFragment)
                    getFragmentManager().findFragmentById(R.id
                            .news_content_fragment);
            newsContentFragment.refresh(news.getTitle(), news.getContent());
        } else {
            //单页模式
            NewsContentActivity.actionStart(getActivity(), news.getTitle(),
                    news.getContent());
        }
    }
}
