#include "Global.hpp"
#include "Queues.hpp"
#include "Task.hpp"
#include "Memory.hpp"

int const FIFO = 5;       //First In First Out
const int SJF  = 6;       //Shortest Job First scheduling algorithm
const int RR   = 7;       //Round Robin
const int ASJF = 8;       //Approximate Shortest Job First scheduling algorithm
int algorithm = FIFO;     //The scheduling algorithm chosen, default fifo
float CPU_runtime = 1.2;  //How long each CPU is allowed to run during RR scheduling

int cache_Size;      //The number of logical pages
int page_Distribution;    //The upper bound for the page distribution
int cache_Miss = 0;       //The total times a cache miss occurs
float cache_Miss_Penalty;    //The penalty for a cache miss
Memory m(cache_Size);
const int MEMORYFIFO = 10;  //Memory algorithm fifo
const int MEMORYLRU  = 11;  //Memory algorithm least recently used
const int MEMORYMRU  = 12;  //Memory algorithm most recently used
const int MEMORYSC   = 13;  //Memory algorithm second chance
int memory_algorithm = MEMORYFIFO;  //The chosen memory algorithm


float current_Time = 0.0; //The current time of the process in the priority queue
int idle_CPUs;            //The number of current CPUs not doing anything
float switch_Cost;        //The time it takes to switch from IO to CPU or vice versa
int task_Mix;             //The percentage of CPU bound tasks from the total 100% of tasks
float task_Frequency;     //The rate at which new processes are added to the system
int IO_devices;           //The number of total unique IO devices
float CPU_utilization = 0.0; //The time that a CPU was actually doing something
int CPUcount;             //The total number of CPUs

Queues q;
std::vector<std::queue<Task*> > IOQ_vector;
std::vector<float> latency_Times;
std::vector<float> response_Times;

std::vector<float> avgResponseTimes;
std::vector<float> avgEfficiency;
std::vector<float> avgThroughPut;
std::vector<float> avgCacheMisses;
std::vector<float> avgLatencyTimes;
