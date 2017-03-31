package com.prework;

import java.io.*;

/**
 * Created by alvin on 2017/3/29.
 */
public class ReadFile {
    public static final String CTG1="000";
    public static final String CTG2="110";
    public static final String CTG3="111";
    public static int R[]=new int[24];
    public static String allline[][]=new String[31][2];
    public static int PC = 128;
    public static File simulation = new File("simulation.txt");
    public static void init_R(){
        for(int i=0;i<R.length;i++)
            R[i]=0;
    }
    public static String tobincode(int integer){

        System.out.println(Integer.toBinaryString(121));
        String bincode="";
        String s="";
        if(integer==0)
            bincode="00000000000000000000000000000000";
        else
            if(integer==1)
                bincode="00000000000000000000000000000001";
            else
                bincode=Integer.toBinaryString(integer);
        if(bincode.length()<32){

            for(int i=0;i<32-bincode.length();i++){
                s+="0";
            }
        }
        bincode = s+bincode;
        //System.out.println("show toBinaryString:"+bincode+" its length:"+bincode.length());
        return bincode;
    }
    public static String disass_instruction(String bincode){
        String instruction="";
        String inst_opcode;
        int inst_loca1;//rs
        int inst_loca2;//rt
        int inst_loca3;//rd or imme_value
        String category=bincode.substring(0,3);
        switch (category){
            case CTG1:
               // System.out.println("category1");
                inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,21),2);
                //System.out.println("what's in switch:"+bincode.substring(3,7));
                switch (bincode.substring(3,6)) {
                    case "000":
                        inst_opcode = "J";
                        inst_loca3 = Integer.valueOf(bincode.substring(6,32),2);
                        instruction ="J #"+inst_loca3*4;// offset << 2
                        break;
                    case "010":
                        inst_opcode = "BEQ";
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2)*4;
                        if(bincode.charAt(16)=='1') inst_loca3=-inst_loca3;
                        instruction="BEQ R"+inst_loca1+", R"+inst_loca2+", #"+inst_loca3;
                        break;
                    case "100":
                        inst_opcode = "BGTZ";
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2)*4;
                        if(bincode.charAt(16)=='1') inst_loca3=-inst_loca3;
                        instruction="BGTZ R"+inst_loca1+", #"+inst_loca3;
                        break;
                    case "101":
                        inst_opcode = "BREAK";
                        instruction = "BREAK";
                        break;
                    case "110":
                        inst_opcode = "SW";
                        inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                        inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2);
                        instruction="SW R"+inst_loca2+", "+inst_loca3+"(R"+inst_loca1+")";
                        break;
                    case "111":
                        inst_opcode = "LW";
                        inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                        inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2);
                        instruction="LW R"+inst_loca2+", "+inst_loca3+"(R"+inst_loca1+")";
                        break;
                }
                break;
            case CTG2:
                //System.out.println("category2");
                inst_loca1 = Integer.valueOf(bincode.substring(3,8),2);
                inst_loca2 = Integer.valueOf(bincode.substring(8,13),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,21),2);
                switch (bincode.substring(13,16)) {
                    case "000":
                        inst_opcode = "ADD";
                        //R[inst_loca3]=R[inst_loca1]+R[inst_loca2];
                        instruction="ADD R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "001":
                        inst_opcode = "SUB";
                        instruction="SUB R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "010":
                        inst_opcode = "MUL";
                        instruction="MUL R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "011":
                        inst_opcode = "AND";
                        instruction="AND R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "100":
                        inst_opcode = "OR";
                        instruction="OR R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "101":
                        inst_opcode = "XOR";
                        instruction="XOR R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                    case "110":
                        inst_opcode = "NOR";
                        instruction="NOR R"+inst_loca3+", R"+inst_loca1+", R"+inst_loca2;
                        break;
                }
                break;
            case CTG3:
               // System.out.println("category3");
                inst_loca1 = Integer.valueOf(bincode.substring(3,8),2);
                inst_loca2 = Integer.valueOf(bincode.substring(8,13),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,32),2);
                switch (bincode.substring(13,16)) {
                    case "000":
                        inst_opcode = "ADDI";
                        instruction="ADDI R"+inst_loca2+", R"+inst_loca1+", #"+inst_loca3;
                        break;
                    case "001":
                        inst_opcode = "ANDI";
                        instruction="ANDI R"+inst_loca2+", R"+inst_loca1+", #"+inst_loca3;
                        break;
                    case "010":
                        inst_opcode = "ORI";
                        instruction="ORI R"+inst_loca2+", R"+inst_loca1+", #"+inst_loca3;
                        break;
                    case "011":
                        inst_opcode = "XORI";
                        instruction="XORI R"+inst_loca2+", R"+inst_loca1+", #"+inst_loca3;
                        break;
                }
                break;
        }
        return instruction;
    }
    public static String num_instruction(String bincode){
        int flag=0;
        int num;
        if(bincode.charAt(0)=='1'){
            while(flag<32&&bincode.charAt(flag)=='1') flag++;
            String code1 =bincode.substring(flag-1,32);
            num = Integer.valueOf(code1,2);
            String comp = Integer.toBinaryString(num-1);
            int zeronum = code1.length()-comp.length();
            String zerostring = "";
            for(int i=0;i<zeronum;i++) zerostring+="0";
            comp = zerostring+comp;
            char[] compchar=comp.toCharArray();
            for(int i=0;i<compchar.length;i++){
                if(compchar[i]=='0')
                    compchar[i]=49;
                else
                    compchar[i]=48;
            }
            comp = String.valueOf(compchar);
            num = Integer.valueOf(comp,2);
            num = -num;
            //System.out.println("num="+num);
        }
        else{
            while(flag<32&&bincode.charAt(flag)=='0') flag++;
            num=Integer.valueOf(bincode.substring(flag-1,32),2);
        }
        return String.valueOf(num);
    }
    /**
     * 对带有地址的指令进行执行，返回下一条指令PC
     * @param instruction
     * @param address_pc
     * @return next_address_pc
     */
    public static int exec_instruction(String instruction,int address_pc,int cycle){
        String bincode = instruction;
        int next_address_pc = address_pc+4;//默认为执行下一条指令
        int inst_loca1;//rs
        int inst_loca2;//rt
        int inst_loca3;//rd or imme_value
        String category=bincode.substring(0,3);
        String inst_opcode;
        switch (category){
            case CTG1:
                inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,21),2);
                switch (bincode.substring(3,6)) {
                    case "000"://J
                        inst_loca3 = Integer.valueOf(bincode.substring(6,32),2);
                        next_address_pc=inst_loca3*4;
                        System.out.println("J next PC============>"+next_address_pc);
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "010":
                        inst_opcode = "BEQ";
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2)*4;
                        if(bincode.charAt(16)=='1') inst_loca3=-inst_loca3;
                        if(R[inst_loca1]==R[inst_loca2])
                            next_address_pc=address_pc+inst_loca3+4;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "100":
                        inst_opcode = "BGTZ";
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2)*4;
                        if(bincode.charAt(16)=='1') inst_loca3=-inst_loca3;
                        if(R[inst_loca1]>0)
                            next_address_pc=address_pc+inst_loca3+4;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "101":
                        inst_opcode = "BREAK";
                        next_address_pc = -1;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "110":
                        inst_opcode = "SW";
                        inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                        inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2);

                        //step1:get rt value
                        int rt=R[inst_loca2];
                        //allline address
                        int valusaddr = (R[inst_loca1]+inst_loca3-128)/4;
                        //rt value to binary
                        String changedbincode = tobincode(rt);
                        // save bincode to corresponding address
                        allline[valusaddr][1]=changedbincode;
                        //System.out.println("looK====>"+valusaddr+"changedbincode:"+changedbincode);
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "111":
                        inst_opcode = "LW";
                        inst_loca1 = Integer.valueOf(bincode.substring(6,11),2);
                        inst_loca2 = Integer.valueOf(bincode.substring(11,16),2);
                        inst_loca3 = Integer.valueOf(bincode.substring(18,32),2);
                        //instruction="LW R"+inst_loca2+", "+inst_loca3+"(R"+inst_loca1+")";
                        int intervalue=0;
                        int index = (R[inst_loca1]+inst_loca3-128)/4;

                        intervalue = Integer.valueOf(num_instruction(allline[index][1]));
                        R[inst_loca2]=intervalue;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                }
                break;
            case CTG2:
                //System.out.println("category2");
                inst_loca1 = Integer.valueOf(bincode.substring(3,8),2);
                inst_loca2 = Integer.valueOf(bincode.substring(8,13),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,21),2);
                switch (bincode.substring(13,16)) {
                    case "000":
                        inst_opcode = "ADD";
                        R[inst_loca3]=R[inst_loca1]+R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "001":
                        inst_opcode = "SUB";
                        R[inst_loca3]=R[inst_loca1]-R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "010":
                        inst_opcode = "MUL";
                        R[inst_loca3]=R[inst_loca1]*R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "011":
                        inst_opcode = "AND";
                        R[inst_loca3]=R[inst_loca1]&R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "100":
                        inst_opcode = "OR";
                        R[inst_loca3]=R[inst_loca1]|R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "101":
                        inst_opcode = "XOR";
                        R[inst_loca3]=R[inst_loca1]^R[inst_loca2];
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "110":
                        inst_opcode = "NOR";
                        R[inst_loca3]=~(R[inst_loca1]|R[inst_loca2]);
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                }
                break;
            case CTG3:
                // System.out.println("category3");
                inst_loca1 = Integer.valueOf(bincode.substring(3,8),2);
                inst_loca2 = Integer.valueOf(bincode.substring(8,13),2);
                inst_loca3 = Integer.valueOf(bincode.substring(16,32),2);// outofarray??
                switch (bincode.substring(13,16)) {
                    case "000":
                        inst_opcode = "ADDI";
                        R[inst_loca2]=R[inst_loca1]+inst_loca3;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "001":
                        inst_opcode = "ANDI";
                        R[inst_loca2]=R[inst_loca1]+inst_loca3;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "010":
                        inst_opcode = "ORI";
                        R[inst_loca2]=R[inst_loca1]|inst_loca3;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                    case "011":
                        inst_opcode = "XORI";
                        R[inst_loca2]=R[inst_loca1]|inst_loca3;
                        show_register(bincode,cycle,address_pc,simulation);
                        break;
                }
                break;
        }
        return next_address_pc;
    }
    public static void show_register(String bincode,int cycle,int address_pc,File filename){
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(filename,true);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            System.out.println("--------------------");
            bufferedWriter.write("--------------------");
            bufferedWriter.newLine();

            System.out.println("Cycle:"+cycle+"\t"+address_pc+"\t"+disass_instruction(bincode)+"\n");
            bufferedWriter.write("Cycle:"+cycle+"\t"+address_pc+"\t"+disass_instruction(bincode)+"\n");
            bufferedWriter.newLine();

            System.out.println("Registers");
            bufferedWriter.newLine();
            for(int i=0;i<R.length;i++) {
                if(i>0&&i%8==0) System.out.println();
                System.out.print(R[i]+" ");
            }

            System.out.println("\nData");
            int j=0,count;
            while(!disass_instruction(allline[j][1]).equals("BREAK")) j++;
            for(j=j+1,count=0;j<allline.length-1;j++,count++){
                if(count>0&&count%8==0) System.out.println();
                // System.out.println("====>>>>"+allline[j][1]);
                System.out.print(num_instruction(allline[j][1])+" ");

            }

            System.out.println();


        }catch (Exception e){
            e.printStackTrace();
        }


        System.out.println("--------------------");
        System.out.println("Cycle:"+cycle+" "+address_pc+disass_instruction(bincode));
        System.out.println();

        System.out.println("Registers");
        for(int i=0;i<R.length;i++) {
            if(i>0&&i%8==0) System.out.println();
            System.out.print(R[i]+" ");
        }

        System.out.println("\nData");
        int j=0,count;
        while(!disass_instruction(allline[j][1]).equals("BREAK")) j++;
        for(j=j+1,count=0;j<allline.length-1;j++,count++){
            if(count>0&&count%8==0) System.out.println();
           // System.out.println("====>>>>"+allline[j][1]);
            System.out.print(num_instruction(allline[j][1])+" ");

        }

        System.out.println();

    }
    public static void main(String[] args){
        String s = "";

        // here get source file
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream("/Users/alvin/Documents/coding/MIPSsim/sample.txt");
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            for(int i=0,insaddr=128;(s = bufferedReader.readLine())!= null;i++,insaddr+=4) {
                allline[i][1] = s;//[1]-code
                allline[i][0] = String.valueOf(insaddr);//[0]-address
            }
            //for(int i=0;i<allline.length-1;i++)
                //System.out.println(allline[i][1]+"\t"+allline[i][0]);
        }catch (FileNotFoundException e){
            System.out.println("error:cannot find your input file!");
            System.out.println(System.getProperty("user.dir"));
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("error:failed to load you input file!");
            e.printStackTrace();
        }finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                fileInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // disassemble instructions and output disassembly.txt
        FileOutputStream fileOutputStream = null;
        try {
            File disassem = new File("disassembly.txt");
            fileOutputStream = new FileOutputStream(disassem);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            int i;
            //System.out.println("=====outputfile===");
            for(i=0;i<allline.length-1;i++){
                if(disass_instruction(allline[i][1]).equals("BREAK")){
                    String disassab = disass_instruction(allline[i][1]);
                    bufferedWriter.write(allline[i][1]+"\t"+allline[i][0]+" "+disassab);
                    bufferedWriter.newLine();
                    break;
                }
                String disassab = disass_instruction(allline[i][1]);
                bufferedWriter.write(allline[i][1]+"\t"+allline[i][0]+" "+disassab);
                bufferedWriter.newLine();
            }

            for(i=i+1;i<allline.length-1;i++) {
                String num_ins=num_instruction(allline[i][1]);
                    bufferedWriter.write(allline[i][1]+"\t"+allline[i][0]+" "+num_ins);
                    bufferedWriter.newLine();
            }
            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // execute instructions and output simulation.txt
        int PC = Integer.valueOf(allline[0][0]);
        int cycle = 0;
        while(PC!=-1){
            //System.out.println("==========================>PC :"+PC);
            cycle += 1;
            PC = exec_instruction(allline[(PC-128)/4][1], PC,cycle);
            //System.out.println("==========================>NextPC :"+PC);
        }
        // end of the simulation
    }

}
