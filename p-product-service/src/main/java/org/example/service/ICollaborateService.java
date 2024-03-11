package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.CollaborateDO;
import org.example.request.CreateCollaborateReq;
import org.springframework.web.multipart.MultipartFile;

public interface ICollaborateService {

    AjaxResult uploadPaperTemp(MultipartFile file, String info);

    AjaxResult meCreateTemp();

    AjaxResult toCollaborate(CreateCollaborateReq req);

    AjaxResult meCreateCollaborate();

    AjaxResult toPaper(String collaborateId);

    AjaxResult collaborateList(String cartoonId);
}
