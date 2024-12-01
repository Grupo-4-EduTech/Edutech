import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.github.pjfanning.xlsx.StreamingReader;
import org.springframework.jdbc.core.RowMapper;

public class ApachePOI {

    String RESET = "\u001B[0m";
    String ERROR = "\u001B[31mERROR" + RESET;
    String WARN = "\u001B[33mWARN" + RESET;
    String INFO = "\u001B[34mINFO" + RESET;

    public List<Questao> extrairQuestoes(String filename, InputStream file) throws Exception {
        JSONObject json = new JSONObject();

        try {
            Connection dbConnection = new Connection();
            JdbcTemplate connection = dbConnection.getConnection();

            json.put("text", """
                    ========================= :small_orange_diamond: *AVISO* :small_orange_diamond: ========================= 
                    
                    :small_orange_diamond: Novas informações foram adicionadas!
                    """);

            Slack.sendMessage(json);

            System.out.printf("\n%s - %s: Iniciando leitura do arquivo %s", getDataHora(), INFO, filename);

            json.put("text", " ========================= :small_blue_diamond: *INFO* :small_blue_diamond: ========================= ");
            Slack.sendMessage(json);

            json.put("text", ":small_blue_diamond: Iniciando leitura do arquivo '%s'".formatted(filename));
            Slack.sendMessage(json);

            connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)", getDataHoraBD(), "Novas informações foram adicionadas", null, 0000000, "Aviso");

            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            List<Questao> questoesExtraidas = new ArrayList<>();

            Materia MT = new Materia(1, "MT");
            Materia LP = new Materia(2, "LP");

            for (Row row : sheet) {
                if (row == null) {
                    continue;
                }
                if (row.getRowNum() == 0) {
                    System.out.printf("\n%s - %s: Lendo cabeçalho", getDataHora(), INFO);
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        Integer cellIndex = cell.getColumnIndex();
                        String coluna = row.getCell(cellIndex).getStringCellValue();
                        System.out.printf("\n%s - %s: Coluna %d - %s", getDataHora(), INFO, cellIndex, coluna);
                    }
                    continue;
                }

                Questao questao = new Questao();

                if (row.getCell(2).getNumericCellValue() == 12) {
                    if (row.getCell(3).getNumericCellValue() == 1 || row.getCell(3).getNumericCellValue() == 2) {
                        if (row.getCell(1).getStringCellValue().equals("LP")) {
                            questao.setMateria(LP);
                        } else if (row.getCell(1).getStringCellValue().equals("MT")) {
                            questao.setMateria(MT);
                        }
                        questao.setBloco((int) row.getCell(3).getNumericCellValue());
                        questao.setNumero((int) row.getCell(4).getNumericCellValue());
                        questao.setIdQuestao((int) row.getCell(5).getNumericCellValue());
                        questao.setDescritor(row.getCell(6).getStringCellValue());
                        questao.setRespostaCorreta(row.getCell(7).getStringCellValue());

                        questoesExtraidas.add(questao);
                    }
                }
            }
            System.out.printf("\n%s - %s: Leitura de arquivo finalizada", getDataHora(), INFO);
            workbook.close();
            json.put("text", ":small_blue_diamond: Leitura do arquivo finalizada");
            Slack.sendMessage(json);

            System.out.printf("\n%s - %s: Iniciando inserção no banco de dados", getDataHora(), INFO);
            json.put("text", ":small_blue_diamond: Iniciando inserção no banco de dados");
            Slack.sendMessage(json);

            for (Questao questao : questoesExtraidas) {
                if (questao.getIdQuestao() == 0) {
                    continue;
                }
                connection.update("INSERT IGNORE INTO questao (idQuestao, numero, bloco, respostaCorreta, descritor, fkMateria) VALUES(?, ?, ?, ?, ?, ?)", questao.getIdQuestao(), questao.getNumero(), questao.getBloco(), questao.getRespostaCorreta(), questao.getDescritor(), questao.getMateria().getIdMateria());
            }

            System.out.printf("\n%s - %s: Inserção finalizada", getDataHora(), INFO);
            json.put("text", ":small_blue_diamond: Inserção finalizada");
            Slack.sendMessage(json);

            return questoesExtraidas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extrairDadosEM(String filename, InputStream file, List<Questao> questoes) {
        JSONObject json = new JSONObject();
        IOUtils.setByteArrayMaxOverride(1600000000);
        int seed = 1;
        try {
            Connection dbConnection = new Connection();
            JdbcTemplate connection = dbConnection.getConnection();

            System.out.printf("\n%s - %s: Iniciando leitura do arquivo %s", getDataHora(), INFO, filename);
            Workbook workbook = StreamingReader.builder().rowCacheSize(500).bufferSize(6144).open(file);

            json.put("text", ":small_blue_diamond: Iniciando leitura do arquivo '%s'".formatted(filename));
            Slack.sendMessage(json);

            Sheet sheet = workbook.getSheetAt(0);

            List<Escola> escolasExtraidas = new ArrayList<>();
            List<Turma> turmasExtraidas = new ArrayList<>();
            List<Aluno> alunosExtraidos = new ArrayList<>();
            List<RespostaAluno> respostasExtraidas = new ArrayList<>();

            List<Integer> relevante = List.of(5, 8, 9, 10, 22, 23, 24, 25, 33, 38, 43, 45, 46);
            List<String> listaNomeTurmas = new ArrayList<>(Arrays.asList(
                    "3ºA", "3ºB", "3ºC", "3ºD", "3ºE", "3ºF", "3ºG", "3ºH"
            ));
            List<Integer> listaIdEscolas = new ArrayList<>();

            Integer contador = 0;

            for (Row row : sheet) {
                if (row == null) {
                    continue;
                }
                if (row.getRowNum() == 0) {
                    System.out.printf("\n%s - %s: Lendo cabeçalho", getDataHora(), INFO);
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        if (relevante.contains(cell.getColumnIndex())) {
                            Integer cellIndex = cell.getColumnIndex();
                            String coluna = row.getCell(cellIndex).getStringCellValue();
                            System.out.printf("\n%s - %s: Coluna %d - %s", getDataHora(), INFO, cellIndex, coluna);
                        }
                    }
                    continue;
                }

                if (row.getRowNum() % 500 == 0) {
                    System.out.printf("\n%s - %s: Lendo linha %d", getDataHora(), INFO, row.getRowNum());
                }

                if (row.getCell(16) != null && row.getCell(16).getNumericCellValue() == 1) {

                    Diretoria diretoria = new Diretoria();
                    Escola escola = new Escola();
                    Turma turma = new Turma();
                    Aluno aluno = new Aluno();
                    RespostaAluno respostaAluno = new RespostaAluno();

                    List<String> respostas = new ArrayList<>();

                    Faker faker = new Faker(new Locale("pt-BR"), new Random(seed));

                    if (row.getCell(5) != null && row.getCell(5).getNumericCellValue() != 0) {
                        escola.setIdEscola((int) row.getCell(5).getNumericCellValue());
                    }
                    if (row.getCell(8) != null && row.getCell(8).getNumericCellValue() != 0) {
                        turma.setIdTurma((int) row.getCell(8).getNumericCellValue());
                    }
                    if (row.getCell(9) != null && row.getCell(9).getNumericCellValue() != 0) {
                        turma.setSerie((int) row.getCell(9).getNumericCellValue());
                    }
                    if (row.getCell(10) != null && !(row.getCell(10).getStringCellValue().equals(""))) {
                        aluno.setIdAluno(row.getCell(10).getStringCellValue());
                    }
                    if (row.getCell(22) != null && !(row.getCell(22).getStringCellValue().equals(""))) {
                        List<String> LP1 = List.of(row.getCell(22).getStringCellValue().split(""));
                        respostas.addAll(LP1);
                    }
                    if (row.getCell(23) != null && !(row.getCell(23).getStringCellValue().equals(""))) {
                        List<String> LP2 = List.of(row.getCell(23).getStringCellValue().split(""));
                        respostas.addAll(LP2);
                    }
                    if (row.getCell(24) != null && !(row.getCell(24).getStringCellValue().equals(""))) {
                        List<String> MT1 = List.of(row.getCell(24).getStringCellValue().split(""));
                        respostas.addAll(MT1);
                    }
                    if (row.getCell(25) != null && !(row.getCell(25).getStringCellValue().equals(""))) {
                        List<String> MT2 = List.of(row.getCell(25).getStringCellValue().split(""));
                        respostas.addAll(MT2);
                    }
                    if (row.getCell(33) != null && row.getCell(33).getNumericCellValue() != 0) {
                        aluno.setProficienciaLP(row.getCell(33).getNumericCellValue());
                    }
                    if (row.getCell(38) != null && row.getCell(38).getNumericCellValue() != 0) {
                        aluno.setProficienciaMT(row.getCell(38).getNumericCellValue());
                    }
                    if (row.getCell(43) != null && row.getCell(43).getNumericCellValue() != 0) {
                        aluno.setNivel((int) row.getCell(43).getNumericCellValue());
                    }
                    if (row.getCell(45) != null && !row.getCell(45).getStringCellValue().equals("")) {
                        aluno.setSexo(switch (row.getCell(45).getStringCellValue()) {
                            case "A" -> "M";
                            case "B" -> "F";
                            default -> null;
                        });
                    }
                    if (row.getCell(46) != null || !row.getCell(46).getStringCellValue().equals("")) {
                        aluno.setIdade(switch (row.getCell(46).getStringCellValue()) {
                            case "A" -> 16;
                            case "B" -> 17;
                            case "C" -> 18;
                            case "D" -> 19;
                            case "E" -> 20;
                            case "F" -> 21;
                            default -> null;
                        });
                    }

                    diretoria.setIdDiretoria(1); // seria bom conseguir deschumbar isso
                    escola.setNome(faker.university().name()); // sei que os nomes são meio estranhos, mas é o que tem pra hoje
                    escola.setLogradouro(faker.address().streetName());
                    escola.setNumLogradouro(Integer.parseInt(faker.address().streetAddressNumber()));
                    escola.setIdRegiao(ThreadLocalRandom.current().nextInt(1, 6));
                    aluno.setNome(faker.name().fullName());
                    seed++;

                    escola.setDiretoria(diretoria);
                    turma.setEscola(escola);
                    aluno.setTurma(turma);
                    respostaAluno.setAluno(aluno);
                    respostaAluno.setResposta(respostas);

                    if (!listaIdEscolas.contains(turma.getEscola().getIdEscola())) {
                        listaIdEscolas.add(turma.getEscola().getIdEscola());
                        contador = 0;
                        turma.setNome(listaNomeTurmas.get(contador));
                    } else {
                        turma.setNome(listaNomeTurmas.get(contador));
                    }
                    contador++;

                    escolasExtraidas.add(escola);
                    turmasExtraidas.add(turma);
                    alunosExtraidos.add(aluno);
                    respostasExtraidas.add(respostaAluno);


                }
            }

            workbook.close();

            System.out.printf("\n%s - %s: Leitura de arquivo finalizada", getDataHora(), INFO);
            json.put("text", ":small_blue_diamond: Leitura de arquivo finalizada");
            Slack.sendMessage(json);

            System.out.printf("\n%s - %s: Iniciando inserção no banco de dados", getDataHora(), INFO);
            json.put("text", ":small_blue_diamond: Iniciando inserção no banco de dados");
            Slack.sendMessage(json);

            for (Escola escola : escolasExtraidas) {
                if (escola.getIdEscola() == 0) {
                    continue;
                }

                connection.update("INSERT IGNORE INTO escola (idEscola, nome, logradouro, numLogradouro, fkDiretoria, idRegiao) VALUES(?, ?, ?, ?, ?, ?)", escola.getIdEscola(), escola.getNome(), escola.getLogradouro(), escola.getNumLogradouro(), escola.getDiretoria().getIdDiretoria(), escola.getIdRegiao());
            }

            for (Turma turma : turmasExtraidas) {
                if (turma.getIdTurma() == 0) {
                    continue;
                }
                connection.update("INSERT IGNORE INTO turma (idTurma, nome, serie, fkEscola, fkDiretoria) VALUES(?, ?, ?, ?, ?)", turma.getIdTurma(), turma.getNome(), turma.getSerie(), turma.getEscola().getIdEscola(), turma.getEscola().getDiretoria().getIdDiretoria());
            }

            for (Aluno aluno : alunosExtraidos) {
                if (aluno.getIdAluno() == null || aluno.getIdAluno().equals(0)) {
                    continue;
                }
                connection.update("INSERT IGNORE INTO aluno (idAluno, nome, idade, sexo, proficienciaMT, proficienciaLP, nivel, fkTurma, fkEscola, fkDiretoria) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aluno.getIdAluno(), aluno.getNome(), aluno.getIdade(), aluno.getSexo(), aluno.getProficienciaMT(), aluno.getProficienciaLP(), aluno.getNivel(), aluno.getTurma().getIdTurma(), aluno.getTurma().getEscola().getIdEscola(), aluno.getTurma().getEscola().getDiretoria().getIdDiretoria());
            }

            for (RespostaAluno respostaAluno : respostasExtraidas) {
                if (respostaAluno.getAluno().getIdAluno() == null || respostaAluno.getAluno().getIdAluno().equals(0)) {
                    continue;
                }
                for (int i = 0; i < 52; i++) {
                    if (respostaAluno.getResposta().get(i).equals(".")) {
                        respostaAluno.getResposta().set(i, null);
                    }
                    connection.update("INSERT IGNORE INTO respostaAluno (resposta, fkAluno, fkQuestao) VALUES(?, ?, ?)", respostaAluno.getResposta().get(i), respostaAluno.getAluno().getIdAluno(), questoes.get(i).getIdQuestao());
                }
            }

            consultarDescritores(turmasExtraidas);
            consultarRegioesGrafico();
            consultarRank(turmasExtraidas);
            consultarTurmasPorEscola(turmasExtraidas);
            consultarQtdAlunosAbaixoN5(turmasExtraidas);

            System.out.printf("\n%s - %s: Inserção finalizada", getDataHora(), INFO);
            json.put("text", ":small_blue_diamond: Inserção finalizada");
            Slack.sendMessage(json);

        } catch (IOException e) {
            System.out.printf("\n%s - %s", getDataHora(), ERROR);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public String getDataHora() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public LocalDateTime getDataHoraBD() {
        return LocalDateTime.now();
    }

    // ALERTAS =============================================================================================

    public void consultarDescritores(List<Turma> turmasExtraidas) {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        final Integer[] idTurmaAnterior = {0};

        for (Turma turma : turmasExtraidas) {
            // Executar a consulta SQL
            List<Object[]> descritores = connection.query(
                    "SELECT q.descritor, COUNT(*) AS quantidadeErros, q.fkMateria " +
                            "FROM respostaAluno r " +
                            "JOIN aluno a ON r.fkAluno = a.idAluno " +
                            "JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria " +
                            "JOIN escola e ON t.fkEscola = e.idEscola " +
                            "JOIN diretoria d ON t.fkDiretoria = d.idDiretoria " +
                            "JOIN questao q ON r.fkQuestao = q.idQuestao " +
                            "WHERE t.idTurma = ? AND q.fkMateria = 1 AND r.resposta != q.respostaCorreta " +
                            "GROUP BY q.descritor " +
                            "ORDER BY quantidadeErros DESC " +
                            "LIMIT 1;",
                    new RowMapper<Object[]>() {
                        @Override
                        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                            String descritor = rs.getString("descritor");
                            int quantidadeErros = rs.getInt("quantidadeErros");
                            int fkMateria = rs.getInt("fkMateria");  // Agora estamos acessando fkMateria diretamente


                            String mensagem = "Alunos da turma " + turma.getNome() + " erraram mais questões baseadas no descritor: " + descritor;

                            if (turma.getIdTurma() != idTurmaAnterior[0]) {
                                try {
                                    // Adiciona um delay de 1 segundo (1000 milissegundos)
                                    Thread.sleep(1000);

                                    // Executa a inserção no banco de dados
                                    connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                            getDataHoraBD(), mensagem, 3, turma.getIdTurma(), "Alerta");

                                    idTurmaAnterior[0] = turma.getIdTurma();

                                } catch (InterruptedException e) {
                                    // Trata a exceção caso o thread seja interrompido
                                    e.printStackTrace();
                                }
                            }


                            return new Object[]{descritor, quantidadeErros, fkMateria};
                        }
                    },
                    turma.getIdTurma()  // Passando o ID da turma como parâmetro
            );

            connection.query(
                    "SELECT q.descritor, COUNT(*) AS quantidadeErros, q.fkMateria " +
                            "FROM respostaAluno r " +
                            "JOIN aluno a ON r.fkAluno = a.idAluno " +
                            "JOIN turma t ON a.fkTurma = t.idTurma AND a.fkEscola = t.fkEscola AND a.fkDiretoria = t.fkDiretoria " +
                            "JOIN escola e ON t.fkEscola = e.idEscola " +
                            "JOIN diretoria d ON t.fkDiretoria = d.idDiretoria " +
                            "JOIN questao q ON r.fkQuestao = q.idQuestao " +
                            "WHERE t.idTurma = ? AND q.fkMateria = 2 AND r.resposta != q.respostaCorreta " +
                            "GROUP BY q.descritor " +
                            "ORDER BY quantidadeErros DESC " +
                            "LIMIT 1;",
                    new RowMapper<Object[]>() {
                        @Override
                        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                            String descritor = rs.getString("descritor");
                            int quantidadeErros = rs.getInt("quantidadeErros");
                            int fkMateria = rs.getInt("fkMateria");  // Agora estamos acessando fkMateria diretamente

                            String mensagem = "Alunos da turma " + turma.getNome() + " erraram mais questões baseadas no descritor: " + descritor;

                            if (turma.getIdTurma() != idTurmaAnterior[0]) {
                                try {
                                    // Adiciona um delay de 1 segundo (1000 milissegundos)
                                    Thread.sleep(1000);

                                    // Executa a inserção no banco de dados
                                    connection.update("INSERT INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                            getDataHoraBD(), mensagem, 3, turma.getIdTurma(), "Alerta");

                                    idTurmaAnterior[0] = turma.getIdTurma();

                                } catch (InterruptedException e) {
                                    // Trata a exceção caso o thread seja interrompido
                                    e.printStackTrace();
                                }
                            }


                            return new Object[]{descritor, quantidadeErros, fkMateria};
                        }
                    },
                    turma.getIdTurma()  // Passando o ID da turma como parâmetro
            );


        }
    }

    public void consultarRegioesGrafico() {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        connection.query(
                "SELECT idRegiao AS regiao, SUBSTRING(AVG(proficienciaLP), 1, 4) / 10 AS mediaLP FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY idRegiao HAVING mediaLP IS NOT NULL ORDER BY mediaLP LIMIT 1;",
                new RowMapper<Object[]>() {
                    @Override
                    public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int mediaLP = rs.getInt("mediaLP");
                        int regiao = rs.getInt("regiao");

                        String regiaoNome = null;

                        switch (regiao) {
                            case 1:
                                regiaoNome = "Centro";
                                break;
                            case 2:
                                regiaoNome = "Zona Leste";
                                break;
                            case 3:
                                regiaoNome = "Zona Norte";
                                break;
                            case 4:
                                regiaoNome = "Zona Oeste";
                                break;
                            case 5:
                                regiaoNome = "Zona Sul";
                                break;
                        }

                        String mensagem = "Maior dificuldade em Português na região: " + regiaoNome;

                        // Executa a inserção no banco de dados
                        connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                getDataHoraBD(), mensagem, 1, 0000000, "Alerta");

                        return new Object[]{mediaLP, regiao};
                    }
                }
        );


        connection.query(
                "SELECT idRegiao AS regiao, SUBSTRING(AVG(proficienciaMT), 1, 4) / 10 AS mediaMT FROM escola JOIN aluno ON fkEscola = idEscola GROUP BY idRegiao HAVING mediaMT IS NOT NULL ORDER BY mediaMT LIMIT 1;",
                new RowMapper<Object[]>() {
                    @Override
                    public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                        int mediaMT = rs.getInt("mediaMT");
                        int regiao = rs.getInt("regiao");

                        String regiaoNome = null;

                        switch (regiao) {
                            case 1:
                                regiaoNome = "Centro";
                                break;
                            case 2:
                                regiaoNome = "Zona Leste";
                                break;
                            case 3:
                                regiaoNome = "Zona Norte";
                                break;
                            case 4:
                                regiaoNome = "Zona Oeste";
                                break;
                            case 5:
                                regiaoNome = "Zona Sul";
                                break;
                        }

                        String mensagem = "Maior dificuldade em Matemática na região: " + regiaoNome;

                        // Executa a inserção no banco de dados
                        connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                getDataHoraBD(), mensagem, 1, 0000000, "Alerta");


                        return new Object[]{mediaMT, regiao};
                    }
                }
        );
    }

    public void consultarRank(List<Turma> turmasExtraidas) {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        final Integer[] escolaAnterior = {0};

        for (Turma turma : turmasExtraidas) {
            // Executar a consulta SQL
            connection.query(
                    "SELECT ranking FROM (SELECT RANK() OVER(ORDER BY SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10 DESC) ranking, idEscola, (SUBSTRING((AVG(proficienciaLP)+AVG(proficienciaMT))/2,1,4)/10) media FROM escola JOIN aluno ON aluno.fkEscola = idEscola GROUP BY idEscola) escola WHERE idEscola = ?;",
                    new RowMapper<Object[]>() {
                        @Override
                        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                            int rank = rs.getInt("ranking");  // Agora estamos acessando fkMateria diretamente

                            String mensagem = "Sua escola está no " + rank + "º lugar no ranking geral";

                            if (turma.getEscola().getIdEscola() != escolaAnterior[0]) {
                                // Executa a inserção no banco de dados
                                connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                        getDataHoraBD(), mensagem, 2, turma.getIdTurma(), "Aviso");

                                escolaAnterior[0] = turma.getEscola().getIdEscola();
                            }
                            return new Object[]{rank};
                        }
                    },
                    turma.getEscola().getIdEscola()  // Passando o ID da turma como parâmetro
            );

        }
    }

    public void consultarTurmasPorEscola(List<Turma> turmasExtraidas) {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();

        final Integer[] escolaAnterior = {0};

        for (Turma turma : turmasExtraidas) {
            // Executar a consulta SQL
            connection.query(
                    "SELECT COUNT(*) AS qtdTurmas FROM (SELECT turma.nome, (SUBSTRING((AVG(proficienciaLP) + AVG(proficienciaMT)) / 2, 1, 4) / 10) AS media FROM turma JOIN aluno ON fkTurma = idTurma WHERE turma.fkEscola = ? GROUP BY turma.nome) AS medias_turmas WHERE media < 275;",
                    new RowMapper<Object[]>() {
                        @Override
                        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                            int qtdTurmas = rs.getInt("qtdTurmas");  // Agora estamos acessando fkMateria diretamente

                            String mensagem = qtdTurmas + " turmas estão com desempenho abaixo do nível básico (< 275)";

                            if (turma.getEscola().getIdEscola() != escolaAnterior[0]) {
                                // Executa a inserção no banco de dados
                                connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                        getDataHoraBD(), mensagem, 2, turma.getIdTurma(), "Alerta");

                                escolaAnterior[0] = turma.getEscola().getIdEscola();
                            }
                            return new Object[]{qtdTurmas};
                        }
                    },
                    turma.getEscola().getIdEscola()  // Passando o ID da turma como parâmetro
            );

        }
    }

    public void consultarQtdAlunosAbaixoN5(List<Turma> turmasExtraidas) {
        Connection dbConnection = new Connection();
        JdbcTemplate connection = dbConnection.getConnection();
        final Integer[] escolaAnterior = {0};

        for (Turma turma : turmasExtraidas) {
            // Executar a consulta SQL
            connection.query(
                    "SELECT COUNT(CASE WHEN CAST(SUBSTRING(proficienciaMT, 1, 3) AS UNSIGNED) < 325 THEN 1 END) AS qtdAbaixoMT, COUNT(CASE WHEN CAST(SUBSTRING(proficienciaLP, 1, 3) AS UNSIGNED) < 325 THEN 1 END) AS qtdAbaixoLP FROM aluno WHERE fkturma = ?; ",
                    new RowMapper<Object[]>() {
                        @Override
                        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
                            int qtdAbaixoMT = rs.getInt("qtdAbaixoMT");  // Agora estamos acessando fkMateria diretamente
                            int qtdAbaixoLP = rs.getInt("qtdAbaixoLP");  // Agora estamos acessando fkMateria diretamente

                            String mensagem1 = "Turma " + turma.getNome() + ": " + qtdAbaixoLP + " aluno(s) estão com desempenho abaixo do nível 5 (< 325) em Português!";

                            String mensagem2 = "Turma " + turma.getNome() + ": " + qtdAbaixoMT + " aluno(s) estão com desempenho abaixo do nível 5 (< 325) em Matemática!";

                            if (turma.getEscola().getIdEscola() != escolaAnterior[0]) {
                                // Executa a inserção no banco de dados
                                connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                        getDataHoraBD(), mensagem1, 3, turma.getIdTurma(), "Atenção");

                                connection.update("INSERT IGNORE INTO alerta (dataAlerta, mensagemAlerta, fkCargo, fkTurma, tipoAlerta) VALUES(?, ?, ?, ?, ?)",
                                        getDataHoraBD(), mensagem2, 3, turma.getIdTurma(), "Atenção");

                            }
                            return new Object[]{qtdAbaixoMT, qtdAbaixoLP};
                        }
                    },
                    turma.getIdTurma()
            );

        }
    }
}



