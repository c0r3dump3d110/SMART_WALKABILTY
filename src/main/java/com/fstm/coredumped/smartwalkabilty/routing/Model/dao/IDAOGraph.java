package com.fstm.coredumped.smartwalkabilty.routing.Model.dao;

import com.fstm.coredumped.smartwalkabilty.routing.Model.bo.Graph;

public interface IDAOGraph {
    // to create a graph we need to have the two points source and target
    // then we select the most minumum plygon containing those two points
    public Graph getTheGraph();
}
