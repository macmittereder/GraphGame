//
//  main.cpp
//  Path Pebbling
//
//  Created by Matt Prudente on 11/4/13.
//  Copyright (c) 2013 Matt Prudente. All rights reserved.
//

#include <iostream>
#include "PebblingClass.h"


using namespace std;


int main()
{
    int i, j, k;
    int * p;
    int length=100;
    int Pn[length];
    
    p=Pn; 
    
    
    
   for (i=1; i<20; i++) // Placement of the Pebbles
    {
        for (j=0; j<1000000000; j++)
        {
            for (k=0;k<length;k++)
            {
                p = &Pn[k]; *p=0;
                
            }
            
           
            p = &Pn[i-1]; *p=j;
            
            
            do {
                
                game.Mover(i, Pn);
                game.Defender(i, Pn);
                
            } while (!game.MoverWin(Pn) && game.MoveExists(length, Pn));
            
            if (game.MoverWin(Pn)) break;
            else continue;
            
            delete [] p;
            
        
        }
        cout<< "The Mover wins with "<<j<< " pebbles on vertex v[" << i << "]\n\n";
        p = &Pn[0]; *p = 0;
        
    }

    return 0;
}
