package com.fstm.coredumped.smartwalkabilty.core.danger.controller;

import com.fstm.coredumped.smartwalkabilty.common.controller.DangerReq;
import com.fstm.coredumped.smartwalkabilty.common.controller.DeclareDangerReq;
import com.fstm.coredumped.smartwalkabilty.core.danger.bo.Danger;
import com.fstm.coredumped.smartwalkabilty.core.danger.bo.Declaration;
import com.fstm.coredumped.smartwalkabilty.core.danger.dao.DAODanger;

import java.util.List;

public class DangerCtrl {
    DAODanger daoDanger;

    public DangerCtrl(DAODanger daoDanger){
        this.daoDanger = daoDanger;
    }
    public DangerCtrl(){
        this.daoDanger = new DAODanger();
    }

    public void danger_ctrl(DeclareDangerReq declareDangerReq){
        if(this.daoDanger.createDeclaration(declareDangerReq)){
            System.out.println("Danger inserted succefully");
        } else {

        }
    }

    public List<Declaration> requestDangers(DangerReq dangerReq){
        List<Declaration> declarations = daoDanger.retrieveDeclarations(dangerReq);
        if(declarations == null){
            System.out.println("Problem");
        }
        if(declarations.size() == 0){
            System.out.println("Size == 0");
        }

        return declarations;
    }
}
