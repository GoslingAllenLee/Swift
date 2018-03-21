//
//  DetailMovieViewController.swift
//  MovieApp
//
//  Created by Faisal Riza Rakhmat on 20/03/18.
//  Copyright © 2018 Faisal Riza Rakhmat. All rights reserved.
//

import UIKit
import Alamofire
import AVKit
import AVFoundation
import youtube_ios_player_helper
import FloatRatingView

class DetailMovieViewController: UIViewController, UIScrollViewDelegate,AVPlayerViewControllerDelegate {

    var screenSize = UIScreen.main.bounds
    var idmovie = Int()
    var datadetail: NSDictionary?
    var datavideo: NSArray?
    var lbltitle = UILabel()
    var lbldescription = UILabel()
    var lblruntime = UILabel()
    var lblrate = UILabel()
    var txttitle = String()
    var txtdescription = String()
    var txtruntime = String()
    var idyoutube = String()
    var floatRatingView = FloatRatingView()
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        //self.automaticallyAdjustsScrollViewInsets = false
        //self.edgesForExtendedLayout = []
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navSetting()
        //self.layout()
        self.getData()
        self.getDataVideo()
    }
    
    func navSetting(){
        self.navigationController?.navigationBar.titleTextAttributes = [
            NSAttributedStringKey.foregroundColor : UIColor.white
        ]
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.isTranslucent = true
        self.navigationController?.view.backgroundColor = .clear
        self.view.backgroundColor = UIColor.init(hexString: "#6f5df3")
    }
    
    func layout(){
        var playerView = YTPlayerView()
        playerView.load(withVideoId: idyoutube)
        playerView.frame = CGRect(x: 30, y: 70, width: screenSize.size.width-60, height: screenSize.size.width-100)
        self.view.addSubview(playerView)
        playerView.playVideo()
        
        self.lbltitle.frame = CGRect(x: 30, y: playerView.frame.origin.y + playerView.frame.size.height + 20, width: screenSize.size.width-60, height: 50)
        self.lbltitle.text = txttitle
        self.lbltitle.textColor = UIColor.white
        self.lbltitle.font = UIFont.boldSystemFont(ofSize: 22) //.systemFont(ofSize:16)
        self.view.addSubview(self.lbltitle)
        
        self.lbldescription.frame = CGRect(x: 30, y: lbltitle.frame.origin.y + lbltitle.frame.size.height - 10, width: screenSize.size.width-60, height: 150)
        self.lbldescription.text = txtdescription
        self.lbldescription.numberOfLines = 5
        self.lbldescription.textColor = UIColor.white
        //self.lbldescription.font = UIFont.init().withSize(13)
        self.view.addSubview(self.lbldescription)
        
        self.lblruntime.frame = CGRect(x: 30, y: lbldescription.frame.origin.y + lbldescription.frame.size.height, width: screenSize.size.width-60, height: 50)
        self.lblruntime.text = txtruntime
        self.lblruntime.textColor = UIColor.white
        //self.lblruntime.font = UIFont.init().withSize(13)
        self.view.addSubview(self.lblruntime)
        
        self.lblrate.frame = CGRect(x: 150, y: lblruntime.frame.origin.y + lblruntime.frame.size.height - 15, width: 100, height: 50)
        self.lblrate.textColor = UIColor.white
        //self.lblruntime.font = UIFont.init().withSize(13)
        self.view.addSubview(self.lblrate)
        
        floatRatingView.frame = CGRect(x: 30, y: lblruntime.frame.origin.y + lblruntime.frame.size.height, width: 100, height: 20)
        floatRatingView.fullImage = UIImage(named: "StarFull.png")
        floatRatingView.emptyImage = UIImage(named: "StarEmpty.png")
        floatRatingView.backgroundColor = UIColor.clear
        floatRatingView.contentMode = UIViewContentMode.scaleAspectFit
        floatRatingView.isUserInteractionEnabled = false
        //floatRatingView.ra
        floatRatingView.backgroundColor = UIColor.clear
        self.view.addSubview(floatRatingView)
    }
    
    func getData(){
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US"]
        Alamofire.request("\(URL_GETDETAIL)\(idmovie)", method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                self.datadetail = JSONDict
                print("runtime \(JSONDict)")
                self.txttitle = self.datadetail?.value(forKey: "original_title") as! String
                self.txtdescription = self.datadetail?.value(forKey: "overview") as! String
                self.txtruntime = "Runtime : " + String(describing: self.datadetail?.object(forKey: "runtime") as! Int) + " min"
                self.lblrate.text = "\(self.datadetail?.value(forKey: "vote_average") as! Float)"
                self.floatRatingView.rating = self.datadetail?.value(forKey: "vote_average") as! Float
                self.layout()
            }
        })
    }
    
    func getDataVideo(){
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US"]
        Alamofire.request("\(URL_GETDETAIL)\(idmovie)/videos", method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                if let dictionary = response.result.value as? [String : AnyObject]{
                    self.datavideo = dictionary["results"] as! [[String : AnyObject]] as NSArray
                    if let datamov = self.datavideo?.object(at: 0) as? [String : AnyObject]{
                        //print("detail movie \(datamov["key"] as! String)")
                        self.idyoutube = datamov["key"] as! String
                        self.layout()
                    }
                    //print("detail movie \(self.datavideo?.object(at: 0))")
                }
            }
        })
    }
    
}
