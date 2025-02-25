package ur_os;

import java.util.Comparator;

public class SJF_NP extends Scheduler {

    SJF_NP(OS os) {
        super(os);
    }

    @Override
    public void getNext(boolean cpuEmpty) {
        if (!processes.isEmpty() && cpuEmpty) {        
            // Encontrar el proceso con el menor tiempo total de ejecución
            Process shortestJob = processes.stream().min(Comparator.comparingInt(Process::getRemainingTimeInCurrentBurst)) // Selecciona el proceso más corto
                .orElse(null);

            if (shortestJob != null) {
                processes.remove(shortestJob); // Elimina el proceso seleccionado de la lista
                os.interrupt(InterruptType.SCHEDULER_RQ_TO_CPU, shortestJob); // Asigna el proceso a la CPU
            }
        }
    }

    @Override
    public void newProcess(boolean cpuEmpty) {} // Non-preemptive

    @Override
    public void IOReturningProcess(boolean cpuEmpty) {} // Non-preemptive
}
