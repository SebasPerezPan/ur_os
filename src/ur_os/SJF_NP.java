package ur_os;

import java.util.Comparator;

public class SJF_NP extends Scheduler {

    SJF_NP(OS os) {
        super(os);
    }

    @Override
//    public void getNext(boolean cpuEmpty) {
//        if (!processes.isEmpty() && cpuEmpty) {
//            // Encontrar el proceso con el menor tiempo total de ejecución
//            Process shortestJob = processes.stream().min(Comparator.comparingInt(Process::getRemainingTimeInCurrentBurst)) // Selecciona el proceso más corto
//                .orElse(null);
//
//            if (shortestJob != null) {
//                processes.remove(shortestJob); // Elimina el proceso seleccionado de la lista
//                os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, shortestJob); // Asigna el proceso a la CPU
//            }
//        }
//    }

    // Esta es la implementación del pseudo-codigo usando un proceso de iteración entre los remaining, con el metodo que pillamos xD
    public void getNext(boolean cpuEmpty) {
        if (!processes.isEmpty() && cpuEmpty){
            Process min_BTR_process = null;
            int min_BTR = 9999;

            for (Process p: processes){
                if (p.isCurrentBurstCPU()){
                    int temp = p.getRemainingTimeInCurrentBurst();
                    if(temp < min_BTR) {
                        min_BTR = temp;
                        min_BTR_process = p;
                    } else if (temp == min_BTR) {
                        min_BTR_process = tieBreaker(min_BTR_process, p);

                    }
                }
            }
            os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, min_BTR_process);
            processes.remove(min_BTR_process);
        }
    }

    @Override
    public void newProcess(boolean cpuEmpty) {} // Non-preemptive

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} // Non-preemptive
}
