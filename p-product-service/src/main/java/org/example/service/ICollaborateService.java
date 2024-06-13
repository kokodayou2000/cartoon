package org.example.service;

import org.example.core.AjaxResult;
import org.example.request.collaborate.CreateCollaborateReq;

public interface ICollaborateService {

    AjaxResult uploadPaperTemp(String url, String info);

    AjaxResult meCreateTemp();

    AjaxResult toCollaborate(CreateCollaborateReq req);

    AjaxResult meCreateCollaborate();

    AjaxResult toPaper(String collaborateId);

    AjaxResult collaborateList(String cartoonId);
}
