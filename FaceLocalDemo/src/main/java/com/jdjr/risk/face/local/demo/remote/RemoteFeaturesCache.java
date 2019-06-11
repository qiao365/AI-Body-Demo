package com.jdjr.risk.face.local.demo.remote;

import com.jdjr.risk.face.local.user.RemoteUserFeature;

import java.util.List;

/**
 * Created by michael on 19-4-26.
 */

public class RemoteFeaturesCache {
    public volatile static List<RemoteUserFeature> remoteFeatureAll;
    public volatile static List<RemoteUserFeature> remoteFeatureNew;


}
