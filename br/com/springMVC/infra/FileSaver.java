package com.springMVC.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	//Recebo o endereço do arquivo + o arquivo que veio do formulário
	//Monto o endereço 'path' com o endereço REAL + o endereço base criado do projeto + nome do arquivo
	public String write(String baseFolder, MultipartFile file) {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);//Pego endereço completo
			String path = realPath + "/" + file.getOriginalFilename();//Monto endereço completo + nome do arquivo
			file.transferTo(new File(path));//Envio o ARQUIVO para o endereço completo
			
			//Retorno nome comprimido, somente do endereço + arquivo para salvar no banco
			return baseFolder + "/" + file.getOriginalFilename();//arquivos-sumario/ARQUIVO.jpg
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}

}
