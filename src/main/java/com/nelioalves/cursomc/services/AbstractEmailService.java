package com.nelioalves.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	private SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); // destinatário
		sm.setFrom(sender); // remetente (marxgentil@gmail.com)
		sm.setSubject("Pedido confirmado! Código: " + obj.getId()); // assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); // data do email
		sm.setText(obj.toString()); // corpo do email baseado no toString da classe Pedido
		return sm;
	}
	
}
