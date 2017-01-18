#ifndef SCHEDULING_HPP
#define SCHEDULING_HPP

#include "Task.hpp"

extern void initSchedule();
void init();
void ready(Task* temp);
void popIO(int whichIO);
extern void runSchedule();

#endif
