package com.sdd.bootcamp01;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.sdd.bootcamp01.domain.Transaction;

public class TrxDisplayVm {
	@Wire
	Grid grid;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objList") List<Transaction> objList) {
		Selectors.wireComponents(view, this, false);
		
		grid.setRowRenderer(new RowRenderer<Transaction>() {

			@Override
			public void render(Row row, Transaction data, int index) throws Exception {
				// TODO Auto-generated method stub
				row.appendChild(new Label(String.valueOf(index+1)));
				row.appendChild(new Label(data.getTrxid()));
				row.appendChild(new Label(data.getTrxdate() != null ?
						new SimpleDateFormat("dd MMM yyyy").format(data.getTrxdate()) : ""));
				row.appendChild(new Label(data.getTrxamount() != null ?
						NumberFormat.getInstance().format(data.getTrxamount()) : ""));
			}
		});
		grid.setModel(new ListModelList<>(objList));
	}
			
}
