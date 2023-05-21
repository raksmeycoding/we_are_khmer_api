package com.kshrd.wearekhmer.bookmark.service;

import com.kshrd.wearekhmer.bookmark.model.entity.Bookmark;
import com.kshrd.wearekhmer.bookmark.model.reponse.BookmarkResponse;
import com.kshrd.wearekhmer.bookmark.repository.BookmarkMapper;
import com.kshrd.wearekhmer.history.model.entity.History;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
@AllArgsConstructor
public class IBookServiceImp implements IBookService{

    private final BookmarkMapper bookmarkMapper;
    @Override
    public Bookmark insertBookmark(Bookmark bookmark) {
        return bookmarkMapper.insertBookmark(bookmark);
    }

    @Override
    public Bookmark deleteBookmark(Bookmark bookmark) {
        return bookmarkMapper.deleteBookmark(bookmark);
    }

    @Override
    public List<BookmarkResponse> getAllBookmarkByCurrentId(String userId) {
        return bookmarkMapper.getAllBookmarkByCurrentId(userId);
    }
}
