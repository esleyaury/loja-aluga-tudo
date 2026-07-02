package com.upe.loja.repository;


    import com.upe.loja.repository.entity.Administrador;
    import java.util.Map;
    import java.util.HashMap;
    import java.io.BufferedWriter;
    import java.io.File;
    import java.nio.file.Files;
    import java.util.List;

    public class GerirAdministradorCSV{
        public Map<String, Administrador> carregar(File arquivoAdministradores){

            Map<String, Administrador> listaAdministradores = new HashMap<>();

            try {
                if (!arquivoAdministradores.exists()){
                    arquivoAdministradores.createNewFile();
                    return listaAdministradores;
                }

                List<String> linhas = Files.readAllLines(arquivoAdministradores.toPath());

                for (String linha : linhas){
                    if (linha.trim().isEmpty()){ continue;}

                    String[] dados = linha.split(";");
                    if (dados.length != 4 ){ continue; }

                    String cpf = dados [0];
                    String senha = dados[1];
                    String nome = dados[2];
                    String email = dados[3];
                    //boolean permissaoAdmin = dados[5];

                    Administrador administrador = new Administrador(cpf, senha, nome, email);
                    listaAdministradores.put(cpf, administrador);
                }
            } catch (Exception e){
                System.err.println(e);
                e.printStackTrace();
            }

            return listaAdministradores;
        }

        public void guardarDados(File arquivoAdministradores, Map<String, Administrador> administradores){
                try(BufferedWriter writer = Files.newBufferedWriter(arquivoAdministradores.toPath())){
                    for (Administrador adm : administradores.values()){
                        String linha = String.format("%s;%s;%s;%s", 
                    adm.getCpf(), adm.getSenha(), adm.getNome(), adm.getEmail());

                    writer.write(linha);
                    writer.newLine();
                    }
                }catch (Exception e){
                    System.err.println(e);
                    e.printStackTrace();
                }
            
        }
    
}
