package com.sdd.bootcamp01;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.sdd.bootcamp01.domain.Product;
import com.sdd.bootcamp01.domain.Transaction;


public class TrxListVm {
	@Wire
	Combobox cbProduct;
	@Wire
	Button btSave;
	@Wire
	Grid grid;
	
	boolean isUpdate;
	Transaction obj;
	Transaction objTemp;
	Product product;

	List<Product> productList = new ArrayList<>();
	List<Transaction> objList = new ArrayList<>();
	List<Transaction> objListSelected = new ArrayList<>();

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		grid.setRowRenderer(new RowRenderer<Transaction>() {

			@Override
			public void render(Row row, Transaction data, int index) throws Exception {
				// coloumn index
				row.appendChild(new Label(String.valueOf(index + 1)));
				// column checkbox
				Checkbox ck = new Checkbox();
				ck.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (ck.isChecked()) {
							objListSelected.add(data);
						} else {
							objListSelected.remove(data);
						}
					}
				});
				row.appendChild(ck);
				// column trx id
				row.appendChild(new Label(data.getTrxid()));
				// column trx date
				row.appendChild(new Label(data.getTrxdate() != null ? 
						new SimpleDateFormat("dd MMM yyyy").format(data.getTrxdate())
								: ""));
				// colum product
				row.appendChild(new Label(data.getProduct().getProductname()));
				// column trx quantity
				row.appendChild(new Label(String.valueOf(data.getTrxid())));
				// column trx product price
				row.appendChild(new Label(data.getProduct() != null ? NumberFormat.getInstance().format(data.getProduct().getPrice()): ""));
				// column trx amount
				row.appendChild(new Label(data.getTrxamount() != null ? 
						NumberFormat.getInstance().format(data.getTrxamount()) : ""));

				// button edit
				Button btEdit = new Button("Edit");
				btEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Messagebox.show("Transaction ID : " + data.getTrxid());

						obj = data;
						objTemp = data;
						if (product != null) {
							product = (Product) obj.getProduct();
							cbProduct.setValue(product.getProductname());
						}
						BindUtils.postNotifyChange(TrxListVm.this, "*");

						btSave.setLabel("Update");
						isUpdate = true;
					}
				});

				// button detail
				Button btDetail = new Button("Detail");
				btDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
//						Messagebox.show("Transaction ID : " + data.getTrxid());
						Map<String, Object> map = new HashMap<>();
						map.put("obj", data);
						map.put("index", index + 1);

						Window win = (Window) Executions.createComponents("trxdetail.zul", null, map);
						win.setClosable(true);
						win.doModal();
					}
				});

//				row.appendChild(btDetail);

				Button btDelete = new Button("Delete");
				btDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doDelete(index);
						Messagebox.show("Transaction ID : " + data.getTrxid());
					}
				});

				Hlayout hlayout = new Hlayout();
				hlayout.appendChild(btEdit);
				hlayout.appendChild(btDetail);
				hlayout.appendChild(btDelete);
				row.appendChild(hlayout);
			}
		});
		// doModel();

		obj = new Transaction();
		isUpdate = false;
	}

	@Command
	public ListModelList<Product> getProductModel() {
		productList = new ArrayList<>();

		Product product = new Product();
		product.setProductcode("P001");
		product.setProductname("Air Mineral Oasis");
		product.setPrice(new BigDecimal(5000));
		productList.add(product);
		product = new Product();
		product.setProductcode("P002");
		product.setProductname("Kopi Kapal Fire");
		product.setPrice(new BigDecimal(2500));
		productList.add(product);

		return new ListModelList<>(productList);

	}

	@Command
	public void doDisplay() {
		Map<String, Object> map = new HashMap<>();
		Collections.sort(objListSelected, Comparator.reverseOrder());
		map.put("objList", objListSelected);

		Window win = (Window) Executions.createComponents("trxdisplay.zul", null, map);
		win.setClosable(true);
		win.doModal();
	}
	
	public Validator getFormValidator() {
		return new AbstractValidator() {
			
			@Override
			public void validate(ValidationContext ctx) {
				String trxid = (String) ctx.getProperties("trxid")[0].getValue();
				if (trxid == null || trxid.trim().length() == 0) {
					addInvalidMessage(ctx, "trxid", "Field ini harap dilengkapi");
				}
				
				Date trxdate = (Date) ctx.getProperties("trxdate")[0].getValue();
				if (trxdate == null) {
					addInvalidMessage(ctx, "trxdate", "Field ini harap dilengkapi");
				}
			}
		};
	}

	@Command
	@NotifyChange({"obj", "product"})
	public void doSubmit() {

		if (isUpdate) {
			objList.remove(objTemp);
			objTemp = null;

		}
		obj.setProduct(product);
		if(product != null)
			obj.setTrxamount(product.getPrice().multiply(new BigDecimal(obj.getTrxqty())));
		
		objList.add(obj);
		Collections.sort(objList, Comparator.reverseOrder());
		grid.setModel(new ListModelList<>(objList));
		obj = new Transaction();
		btSave.setLabel("Sumbit");
		isUpdate = false;
		product = new Product();
		cbProduct.setValue(null);
	}

	@Command
	public void doClear() {
		grid.getRows().getChildren().clear();
		objList = new ArrayList<>();
	}

	@Command
	public void doDelete(int index) {
		grid.getRows().getChildren().clear();
		objList.remove(index);
	}

	public Transaction getObj() {
		return obj;
	}

	public void setObj(Transaction obj) {
		this.obj = obj;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
