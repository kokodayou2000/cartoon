package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.cartoon.AddPatternsReq;
import org.example.request.CartoonSaleNumReq;
import org.example.request.cartoon.CreateCartoonReq;
import org.example.request.cartoon.UpdateCartoonReq;

public interface ICartoonService {

    AjaxResult list(String order, int page, int size);

    AjaxResult createCartoon(CreateCartoonReq req);

    AjaxResult uploadCoverImg(String url, String id);

    AjaxResult updateCartoonInfo(UpdateCartoonReq req);

    AjaxResult addPattern(AddPatternsReq req);

    AjaxResult cartoonInfo(String cartoonId);

    AjaxResult chapterList(String cartoonId);

    AjaxResult meJoin();

    AjaxResult meCreate();

    AjaxResult price(String cartoonId);

    AjaxResult sales(CartoonSaleNumReq req);

    AjaxResult cartoonPatternList(String cartoonId);

    Boolean checkCreate(String userId, String cartoonId);


    AjaxResult cartoonListByTag(String tag);



}

