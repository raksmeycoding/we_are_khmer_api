package com.kshrd.wearekhmer.bookmark.service;

import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.history.model.response.HistoryResponse;

import java.util.List;

public interface IBookService {
    Bookmark insertBookmark(Bookmark bookmark);

    Bookmark deleteBookmark(Bookmark bookmark);

    List<BookmarkResponse> getAllBookmarkByCurrentId(String userId);

    List<Bookmark> removeAllBookmark(Bookmark bookmark);
}
