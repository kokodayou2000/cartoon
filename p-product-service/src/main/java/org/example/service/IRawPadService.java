package org.example.service;

import org.example.core.AjaxResult;
import org.example.model.UpdateRawPadReq;

public interface IRawPadService {
    AjaxResult create(String name);

    AjaxResult rawPad(String id);

    AjaxResult UpdateRawPadReq(UpdateRawPadReq req);

    AjaxResult list();
}
