package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;
import org.example.request.CreateRawPadReq;

public interface IRawPadService {


    AjaxResult UpdateRawPadReq(UpdateRawPadReq req);

    AjaxResult createRawPad(CreateRawPadReq padReq);

    AjaxResult workRawPad(String paperId);
}
