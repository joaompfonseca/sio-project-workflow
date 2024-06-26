package com.openkm.workflow.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.openkm.api.OKMDocument;
import com.openkm.api.OKMFolder;
import com.openkm.bean.form.Input;
import com.openkm.bean.form.TextArea;
import com.openkm.bean.Document;

import com.openkm.util.MailUtils;



public class Action2 implements ActionHandler {
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(ExecutionContext executionContext) throws Exception {
		
		String decision = (String) executionContext.getContextInstance().getVariable("Resposta");
		
		String mail = (String) executionContext.getContextInstance().getVariable("Email");
		
		Input assunto = (Input) executionContext.getContextInstance().getVariable("assunto");
		
		TextArea info = (TextArea) executionContext.getContextInstance().getVariable("info");
		
		String uuid = (String) executionContext.getContextInstance().getVariable("uuid");
		String path = OKMFolder.getInstance().getPath(null, uuid);
		String[] temp = path.split("/");
		String number = temp[temp.length - 2];
		
		Document doc = OKMDocument.getInstance().getProperties(null, uuid);
		Calendar temp1 = doc.getCreated();
		
		Date temp2 = temp1.getTime();
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date = format1.format(temp2);
		
		String text = "Na sequência do requiremento " + number + " do dia " + date + " relativo ao assunto: " + assunto.getValue() + ", vimos pelo presente informar que sobre o mesmo recaiu o despacho cujo teor a seguir se transcreve " + decision + ". " + info.getValue() ;
		
		try{
			MailUtils.sendMessage(mail, "Requerimento", text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		executionContext.getToken().signal();
	}
}
