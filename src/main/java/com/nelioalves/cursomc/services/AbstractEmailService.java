package com.nelioalves.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}

	// é protected para dar a alguma subclasse a sobrepor este método
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail()); // destinatário
		sm.setFrom(sender); // remetente (marxgentil@gmail.com)
		sm.setSubject("Solicitação de nova senha"); // assunto
		sm.setSentDate(new Date(System.currentTimeMillis())); // data do email
		sm.setText("Nova senha: " + newPass); // corpo do email 
		return sm;
	}

	protected String htmlFromTemplatePedido(Pedido obj) {
		// o método irá povoar o arquivo HTML confirmacaoPedido.html na pasta src/main/resources/templates/email
		Context context = new Context();
		context.setVariable("pedido", obj); // onde pedido é o apelido dado ao objeto no template(arquivo html) e obj é o objeto da classe Pedido propriamente dito, chamado no parâmetro do método
		return templateEngine.process("email/confirmacaoPedido", context); // por padrão, o thymeleaf busca a pasta resources/templates, por isso, só precisamos colocar o caminho a partir de email/confirmacaoPedido. Não precisa colocar a extensão html
		// o resultado desse return é o email retornado do html em forma de string.
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}
		catch(MessagingException e) {
			sendOrderConfirmationEmail(obj);
			// se o email html der uma exceção, usa o email plano.
		}
				
	}

	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true); 
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender); // remetente (marxgentil@gmail.com)
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId()); // assunto
		mmh.setSentDate(new Date(System.currentTimeMillis())); // data do email
		mmh.setText(htmlFromTemplatePedido(obj), true); // corpo do email baseado no toString da classe Pedido
		
		return mimeMessage;
	}

}
