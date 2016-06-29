/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitordefluxo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodolfo
 */
public class LeitordeFluxo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        MontaPacote pacote = new MontaPacote();
        File nomeArquivo = new File("C:\\Users\\Rodolfo\\Downloads\\video.ts");
        //byte é um tipo que armazena 8 bits
        //Array de pacote de 188 bytes
        byte[] b = new byte[188];
        FileInputStream file = new FileInputStream(nomeArquivo);
        Transport_Packet tp = new Transport_Packet();
        ArrayList<Transport_Packet> TPList = new ArrayList<Transport_Packet>();

        while ((file.read(b)) >= 0) {
                TPList.add(pacote.criaTP(tp, b));
        }
             
        file.close();
        ArrayList<Program_Association_Table> PATList = pacote.tabelaPAT(TPList);
        ArrayList<Integer> program_maps_PIDS = (ArrayList<Integer>) pacote.getPIDS(TPList);
        ArrayList<Program_Map_Table> PMTList = pacote.tabelaPMT(program_maps_PIDS);

        File arquivoFinal = new File("Informações da Tabela.txt");
        PrintWriter pw = new PrintWriter(arquivoFinal);
        pw.println("Numero de pacotes PAT = " + PATList.size());
        pw.println("Numero de pacotes PAT = " + PMTList.size());
        
        for (int i = 0; i < PATList.size(); i++) {
            pw.println((i + 1) + " - " +PATList.get(0));
        }

        for (int i = 0; i < PMTList.size(); i++) {
            pw.println((i + 1) + " - " + PMTList.get(0));
        }
        
        pw.flush();
        pw.close();
    }
}
