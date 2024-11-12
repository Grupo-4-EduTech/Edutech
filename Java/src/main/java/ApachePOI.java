import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.github.javafaker.Faker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.pjfanning.xlsx.StreamingReader;

public class ApachePOI{

    String RESET = "\u001B[0m";
    String ERROR = "\u001B[31mERROR"+RESET;
    String WARN = "\u001B[33mWARN"+RESET;
    String INFO =  "\u001B[34mINFO"+RESET;

    public List<Questao> extrairQuestoes(String filename, InputStream file){
        try{
            Connection dbConnection = new Connection();
            JdbcTemplate connection = dbConnection.getConnection();

            System.out.printf("\n%s - %s: Iniciando leitura do arquivo %s",getDataHora(),INFO,filename);

            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            List<Questao> questoesExtraidas = new ArrayList<>();

            Materia MT = new Materia(1,"MT");
            Materia LP = new Materia(2,"LP");

            for(Row row:sheet) {
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

                if(row.getCell(2).getNumericCellValue()==12){
                    if(row.getCell(3).getNumericCellValue()==1||row.getCell(3).getNumericCellValue()==2) {
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

            workbook.close();

            System.out.printf("\n%s - %s: Leitura de arquivo finalizada", getDataHora(), INFO);
            System.out.printf("\n%s - %s: Iniciando inserção no banco de dados", getDataHora(), INFO);

            for(Questao questao: questoesExtraidas){
                if(questao.getIdQuestao()==0){continue;}
                connection.update("INSERT IGNORE INTO questao (idQuestao, numero, bloco, respostaCorreta, descritor, fkMateria) VALUES(?, ?, ?, ?, ?, ?)", questao.getIdQuestao(),questao.getNumero(),questao.getBloco(),questao.getRespostaCorreta(),questao.getDescritor(),questao.getMateria().getIdMateria());
            }

            System.out.printf("\n%s - %s: Inserção finalizada", getDataHora(), INFO);

            return questoesExtraidas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void extrairDadosEM(String filename, InputStream file, List<Questao> questoes){
        IOUtils.setByteArrayMaxOverride(1600000000);
        int seed = 1;
        try{
            Connection dbConnection = new Connection();
            JdbcTemplate connection = dbConnection.getConnection();

            System.out.printf("\n%s - %s: Iniciando leitura do arquivo %s",getDataHora(),INFO,filename);
            Workbook workbook = StreamingReader.builder().rowCacheSize(500).bufferSize(6144).open(file);

            Sheet sheet = workbook.getSheetAt(0);

            List<Escola> escolasExtraidas = new ArrayList<>();
            List<Turma> turmasExtraidas = new ArrayList<>();
            List<Aluno> alunosExtraidos = new ArrayList<>();
            List<RespostaAluno> respostasExtraidas = new ArrayList<>();

            List<Integer> relevante = List.of(5,8,9,10,22,23,24,25,33,38,43,45,46);

            for (Row row:sheet){
                if(row==null){continue;}
                if(row.getRowNum()==0){
                    System.out.printf("\n%s - %s: Lendo cabeçalho",getDataHora(),INFO);
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while(cellIterator.hasNext()){
                        Cell cell = cellIterator.next();
                        if(relevante.contains(cell.getColumnIndex())){
                            Integer cellIndex = cell.getColumnIndex();
                            String coluna = row.getCell(cellIndex).getStringCellValue();
                            System.out.printf("\n%s - %s: Coluna %d - %s",getDataHora(),INFO,cellIndex,coluna);
                        }
                    }
                    continue;
                }

                if(row.getRowNum() % 500 == 0){
                    System.out.printf("\n%s - %s: Lendo linha %d", getDataHora(), INFO, row.getRowNum());
                }

                if(row.getCell(16)!=null&&row.getCell(16).getNumericCellValue()==1){

                    Diretoria diretoria = new Diretoria();
                    Escola escola = new Escola();
                    Turma turma = new Turma();
                    Aluno aluno = new Aluno();
                    RespostaAluno respostaAluno = new RespostaAluno();

                    List<String> respostas = new ArrayList<>();

                    Faker faker = new Faker(new Locale("pt-BR"),new Random(seed));

                    if(row.getCell(5)!=null&&row.getCell(5).getNumericCellValue()!=0){
                        escola.setIdEscola((int) row.getCell(5).getNumericCellValue());
                    }
                    if(row.getCell(8)!=null&&row.getCell(8).getNumericCellValue()!=0){
                        turma.setIdTurma((int) row.getCell(8).getNumericCellValue());
                    }
                    if(row.getCell(9)!=null&&row.getCell(9).getNumericCellValue()!=0){
                        turma.setSerie((int) row.getCell(9).getNumericCellValue());
                    }
                    if(row.getCell(10)!=null&&!(row.getCell(10).getStringCellValue().equals(""))){
                        aluno.setIdAluno(row.getCell(10).getStringCellValue());
                    }
                    if(row.getCell(22)!=null&&!(row.getCell(22).getStringCellValue().equals(""))){
                        List<String> LP1 = List.of(row.getCell(22).getStringCellValue().split(""));
                        respostas.addAll(LP1);
                    }
                    if(row.getCell(23)!=null&&!(row.getCell(23).getStringCellValue().equals(""))){
                        List<String> LP2 = List.of(row.getCell(23).getStringCellValue().split(""));
                        respostas.addAll(LP2);
                    }
                    if(row.getCell(24)!=null&&!(row.getCell(24).getStringCellValue().equals(""))){
                        List<String> MT1 = List.of(row.getCell(24).getStringCellValue().split(""));
                        respostas.addAll(MT1);
                    }
                    if(row.getCell(25)!=null&&!(row.getCell(25).getStringCellValue().equals(""))){
                        List<String> MT2 = List.of(row.getCell(25).getStringCellValue().split(""));
                        respostas.addAll(MT2);
                    }
                    if(row.getCell(33)!=null&&row.getCell(33).getNumericCellValue()!=0){
                        aluno.setProficienciaLP(row.getCell(33).getNumericCellValue());
                    }
                    if(row.getCell(38)!=null&&row.getCell(38).getNumericCellValue()!=0){
                        aluno.setProficienciaMT(row.getCell(38).getNumericCellValue());
                    }
                    if(row.getCell(43)!=null&&row.getCell(43).getNumericCellValue()!=0){
                        aluno.setNivel((int) row.getCell(43).getNumericCellValue());
                    }
                    if(row.getCell(45)!=null&&!row.getCell(45).getStringCellValue().equals("")){
                        aluno.setSexo(switch (row.getCell(45).getStringCellValue()){
                            case "A" -> "M";
                            case "B" -> "F";
                            default -> null;
                        });
                    }
                    if(row.getCell(46)!=null||!row.getCell(46).getStringCellValue().equals("")){
                        aluno.setIdade(switch (row.getCell(46).getStringCellValue()){
                            case "A" -> 16;
                            case "B" -> 17;
                            case "C" -> 18;
                            case "D" -> 19;
                            case "E" -> 20;
                            case "F" -> 21;
                            default -> null;
                        });
                    }

                    diretoria.setIdDiretoria(1);
                    escola.setNome(faker.university().name());
                    escola.setLogradouro(faker.address().streetName());
                    escola.setNumLogradouro(Integer.parseInt(faker.address().streetAddressNumber()));
                    aluno.setNome(faker.name().fullName());
                    seed++;

                    escola.setDiretoria(diretoria);
                    turma.setEscola(escola);
                    aluno.setTurma(turma);
                    respostaAluno.setAluno(aluno);
                    respostaAluno.setResposta(respostas);

                    escolasExtraidas.add(escola);
                    turmasExtraidas.add(turma);
                    alunosExtraidos.add(aluno);
                    respostasExtraidas.add(respostaAluno);
                }
            }

            workbook.close();

            System.out.printf("\n%s - %s: Leitura de arquivo finalizada", getDataHora(), INFO);
            System.out.printf("\n%s - %s: Iniciando inserção no banco de dados", getDataHora(), INFO);

            for(Escola escola:escolasExtraidas){
                if(escola.getIdEscola()==0){continue;}
                connection.update("INSERT IGNORE INTO escola (idEscola, nome, logradouro, numLogradouro, fkDiretoria) VALUES(?, ?, ?, ?, ?)", escola.getIdEscola(), escola.getNome(), escola.getLogradouro(), escola.getNumLogradouro(), escola.getDiretoria().getIdDiretoria());
            }

            for(Turma turma:turmasExtraidas){
                if(turma.getIdTurma()==0){continue;}
                connection.update("INSERT IGNORE INTO turma (idTurma, serie, fkEscola, fkDiretoria) VALUES(?, ?, ?, ?)", turma.getIdTurma(), turma.getSerie(), turma.getEscola().getIdEscola(), turma.getEscola().getDiretoria().getIdDiretoria());
            }

            for(Aluno aluno:alunosExtraidos){
                if(aluno.getIdAluno()==null||aluno.getIdAluno().equals(0)){continue;}
                connection.update("INSERT IGNORE INTO aluno (idAluno, nome, idade, sexo, proficienciaMT, proficienciaLP, nivel, fkTurma, fkEscola, fkDiretoria) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", aluno.getIdAluno(),aluno.getNome(),aluno.getIdade(),aluno.getSexo(),aluno.getProficienciaMT(),aluno.getProficienciaLP(),aluno.getNivel(),aluno.getTurma().getIdTurma(),aluno.getTurma().getEscola().getIdEscola(),aluno.getTurma().getEscola().getDiretoria().getIdDiretoria());
            }

            for(RespostaAluno respostaAluno:respostasExtraidas){
                if(respostaAluno.getAluno().getIdAluno()==null||respostaAluno.getAluno().getIdAluno().equals(0)){continue;}
                for(int i = 0;i<52;i++){
                    if(respostaAluno.getResposta().get(i).equals(".")){respostaAluno.getResposta().set(i,null);}
                    connection.update("INSERT IGNORE INTO respostaAluno (resposta, fkAluno, fkQuestao) VALUES(?, ?, ?)", respostaAluno.getResposta().get(i),respostaAluno.getAluno().getIdAluno(), questoes.get(i).getIdQuestao());
                }
            }

            System.out.printf("\n%s - %s: Inserção finalizada", getDataHora(), INFO);

        }catch (IOException e){
            System.out.printf("\n%s - %s",getDataHora(),ERROR);
            throw new RuntimeException(e);
        }
    }

    public String getDataHora(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}