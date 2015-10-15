package org.sacredscripture.platform.ws.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.sacredscripture.platform.ws.api.rest.v1.BibleBean;
import com.sacredscripture.platform.ws.api.rest.v1.BibleResource;

public class BibleResourceImpl implements BibleResource {

    @Override
    public List<BibleBean> getBibles(Locale locale) {
        return Arrays.asList(new BibleBean());
    }

}
