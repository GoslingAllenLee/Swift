//
//  HomeCardView.swift
//  MovieApp
//
//  Created by Faisal Riza Rakhmat on 18/03/18.
//  Copyright © 2018 Faisal Riza Rakhmat. All rights reserved.
//

import UIKit

class HomeCardView: UIView {
    
    var imageShow:UIImageView
    var title:UILabel
    var genre:UILabel
    
    override init(frame: CGRect) {
        self.imageShow = UIImageView()
        self.imageShow.layer.cornerRadius = 10
        self.imageShow.clipsToBounds = true
        
        self.title               = UILabel()
        self.title.font          = UIFont.systemFont(ofSize: 13)
        self.title.textAlignment = NSTextAlignment.left
        self.title.textColor     = UIColor.white
        
        self.genre               = UILabel()
        self.genre.font          = UIFont.systemFont(ofSize: 11)
        self.genre.textAlignment = NSTextAlignment.left
        self.genre.textColor     = UIColor.gray
        
        super.init(frame: frame)
        
        self.addSubview(self.imageShow)
        self.addSubview(self.title)
        self.addSubview(self.genre)
        
        //self.backgroundColor = API().hexStringToUIColor(hex: "#26a69a")
        //self.layer.cornerRadius = 10.0
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    override func layoutSubviews() {
        super.layoutSubviews()
        //self.label.frame = self.bounds
        //        self.building.frame     = CGRectMake(0, 0, (frame.width*50/100)-0.5, 20)
        //        self.floor.frame        = CGRectMake((frame.width*50/100)+1, 0, (frame.width*50/100)-0.5, 20)
        //        self.room.frame         = CGRectMake(0, (sie.height * 30/100)+10, frame.width, 25)
        //        self.capacity.frame         = CGRectMake(0, room.frame.origin.y + room.frame.height, frame.width, 25)
        //        self.pipeline.frame     = CGRectMake((frame.width*50/100)-0.5, 0, 1, 21)
        //        self.underline.frame    = CGRectMake(0, 21, frame.width, 1)
    }
    
    
}
