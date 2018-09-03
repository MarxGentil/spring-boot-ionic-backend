package com.nelioalves.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

// Nova Senha
@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();

	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		// testa se o cliente existe
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		char[] vet = new char[10];
		
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3); // random de 3 dígitos
		
		if (opt==0) { // gera um dígito
			return (char) (rand.nextInt(10) + 48); // rand de 0 a 9 mais 0 (48 é o código do 0 na tabela ascc)
		}
		else if (opt==1) { // gera letra maiúscula
			return (char) (rand.nextInt(26) + 65); // rand de 0 a 26 já que são 26 as letras + A (65 é o código da primeira letra)
		}
		else { // gera letra minúscula
			return (char) (rand.nextInt(26) + 97); // rand de 0 a 26 já que são 26 as letras + a (97 é o código da primeira letra)
		}
	}
}
