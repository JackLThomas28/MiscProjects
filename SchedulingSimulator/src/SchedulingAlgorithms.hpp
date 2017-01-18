#ifndef SCHEDULINGALGORITHMS_HPP
#define SCHEDULINGALGORITHMS_HPP

#include "Task.hpp"

void readyFIFO(Task* temp);
void popReadyFIFO();
void readySJF(Task* temp);
void popReadySJF();
void readyRR(Task* temp);
void popReadyRR();
void readyASJF(Task* temp);
void popReadyASJF();

#endif
