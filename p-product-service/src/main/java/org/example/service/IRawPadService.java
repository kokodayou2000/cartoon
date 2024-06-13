package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;
import org.example.request.raw.CreateRawPadReq;

public interface IRawPadService {

    AjaxResult createRawPad(CreateRawPadReq padReq);

    AjaxResult workRawPad(String paperId);

    AjaxResult UpdateRawPadReq(UpdateRawPadReq req);
}
