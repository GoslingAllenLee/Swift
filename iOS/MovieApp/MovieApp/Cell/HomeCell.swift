//
//  HomeCell.swift
//  MovieApp
//
//  Created by Faisal Riza Rakhmat on 18/03/18.
//  Copyright © 2018 Faisal Riza Rakhmat. All rights reserved.
//

import UIKit

class HomeCell: UICollectionViewCell{
    
    var cardView:HomeCardView?
    
    override init(frame: CGRect) {
        cardView = HomeCardView(frame: frame)
        super.init(frame: frame)
        self.cardView?.frame = self.bounds
        self.contentView.addSubview(cardView!)
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setColorBackground(text:String){
        self.cardView?.backgroundColor = UIColor.init(hexString: text) 
    }
    
    override func layoutSubviews() {
        self.cardView?.frame = self.bounds
    }
}


