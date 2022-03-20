package com.fstm.coredumped.smartwalkabilty.core.routing.model.dao;

import com.fstm.coredumped.smartwalkabilty.common.model.bo.GeoPoint;
import com.fstm.coredumped.smartwalkabilty.core.routing.model.bo.Graph;

public interface IDAOGraph {
    // to create a graph we need to have the two points source and target
    // then we select the most minumum plygon containing those two points
    public Graph getTheGraph(GeoPoint source, GeoPoint target);

}
