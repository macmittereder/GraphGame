//
//  main.cpp
//  Playing the Game
//
//  Created by Matt Prudente on 11/20/13.
//  Copyright (c) 2013 Matt Prudente. All rights reserved.
//

#include <iostream>
#include "Pebbling.h"
using namespace std;

int main()
{
    int length;
    int round=1;
    int start=0;
    int end=0;
    
    cout<<"Enter a path length: ";
    cin>> length;
    
    int Pn[length];
    
    game.MakePath(length, Pn);
    cout<<"Original Configuration: \n";
    game.Printarray(length, Pn);
    
    
    do {
        
        cout<< "Round " << round << endl;
        cout<<"Mover's Turn: ";
        game.Mover(length, Pn);
        
        
        cout<<"Defender moves from what to where?\n";
        cin>>start;
        cin>>end;
        game.Defender(length, Pn, start, end);
        
        round++;
    } while (!game.MoverWin(Pn) && game.MoveExists(length, Pn));
    
    
    game.Result(length, Pn);
    
    
    return 0;

    
    


}

