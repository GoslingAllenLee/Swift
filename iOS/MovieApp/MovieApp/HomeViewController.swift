//
//  HomeViewController.swift
//  MovieApp
//
//  Created by Faisal Riza Rakhmat on 17/03/18.
//  Copyright © 2018 Faisal Riza Rakhmat. All rights reserved.
//

import UIKit
import Alamofire

class HomeViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout, UICollectionViewDataSource,UIScrollViewDelegate {
    
    var screenSize = UIScreen.main.bounds
    var txtPopular = UILabel()
    var btnPopular =  UIButton()
    var txtNowplaying = UILabel()
    var btnNowplaying =  UIButton()
    var txtUpcoming = UILabel()
    var btnUpcoming =  UIButton()
    var userDefaults = UserDefaults.standard
    var dataPopular: NSArray?
    var dataNowplaying: NSArray?
    var dataUpcoming: NSArray?
    var cntp = Int()
    var cntn = Int()
    var cntu = Int()
    var scroll = UIScrollView()
    
    lazy var collectionViewPopular:UICollectionView = {
        var cv = UICollectionView(frame: CGRect(x: 20, y: 30, width: self.view.bounds.width-40, height: 200), collectionViewLayout: self.flowLayoutPopular)
        cv.delegate = self
        cv.dataSource = self
        cv.bounces = false
        cv.autoresizingMask = [UIViewAutoresizing.flexibleHeight, UIViewAutoresizing.flexibleWidth]
        cv.register(HomeCell.self, forCellWithReuseIdentifier: "cell")
        cv.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionElementKindSectionHeader, withReuseIdentifier: "header");
        cv.backgroundColor = UIColor.clear
        cv.isPagingEnabled = true
        cv.showsHorizontalScrollIndicator = false
        cv.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cv.tag =  1
        return cv
    }()
    
    lazy var flowLayoutPopular:UICollectionViewFlowLayout = {
        var flow = UICollectionViewFlowLayout()
        flow.sectionInset = UIEdgeInsets(top: 5, left: 1, bottom: 5, right: 0)
        flow.minimumInteritemSpacing = 0
        flow.minimumLineSpacing = 10
        flow.scrollDirection = .horizontal
        return flow
    }()
    
    lazy var collectionViewNowplaying:UICollectionView = {
        var cv = UICollectionView(frame: CGRect(x: 20, y: collectionViewPopular.frame.origin.y + collectionViewPopular.frame.size.height + 40, width: self.view.bounds.width-40, height: 200), collectionViewLayout: self.flowLayoutNowplaying)
        cv.delegate = self
        cv.dataSource = self
        cv.bounces = false
        cv.autoresizingMask = [UIViewAutoresizing.flexibleHeight, UIViewAutoresizing.flexibleWidth]
        cv.register(HomeCell.self, forCellWithReuseIdentifier: "cell")
        cv.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionElementKindSectionHeader, withReuseIdentifier: "header");
        cv.backgroundColor = UIColor.clear
        cv.isPagingEnabled = true
        cv.showsHorizontalScrollIndicator = false
        cv.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cv.tag =  2
        return cv
    }()
    
    lazy var flowLayoutNowplaying:UICollectionViewFlowLayout = {
        var flow = UICollectionViewFlowLayout()
        flow.sectionInset = UIEdgeInsets(top: 5, left: 1, bottom: 5, right: 0)
        flow.minimumInteritemSpacing = 0
        flow.minimumLineSpacing = 10
        flow.scrollDirection = .horizontal
        return flow
    }()
    
    lazy var collectionViewUpcoming:UICollectionView = {
        var cv = UICollectionView(frame: CGRect(x: 20, y: collectionViewNowplaying.frame.origin.y + collectionViewNowplaying.frame.size.height + 40, width: self.view.bounds.width-40, height: 200), collectionViewLayout: self.flowLayoutUpcoming)
        cv.delegate = self
        cv.dataSource = self
        cv.bounces = false
        cv.autoresizingMask = [UIViewAutoresizing.flexibleHeight, UIViewAutoresizing.flexibleWidth]
        cv.register(HomeCell.self, forCellWithReuseIdentifier: "cell")
        cv.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionElementKindSectionHeader, withReuseIdentifier: "header");
        cv.backgroundColor = UIColor.clear
        cv.isPagingEnabled = true
        cv.showsHorizontalScrollIndicator = false
        cv.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cv.tag =  3
        return cv
    }()
    
    lazy var flowLayoutUpcoming:UICollectionViewFlowLayout = {
        var flow = UICollectionViewFlowLayout()
        flow.sectionInset = UIEdgeInsets(top: 5, left: 1, bottom: 5, right: 0)
        flow.minimumInteritemSpacing = 0
        flow.minimumLineSpacing = 10
        flow.scrollDirection = .horizontal
        return flow
    }()
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        //self.automaticallyAdjustsScrollViewInsets = false
        //self.edgesForExtendedLayout = []
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.navSetting()
        self.layout()
        self.getDataToken()
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if collectionView.tag == 1 {
            return cntp
        }else if collectionView.tag == 2 {
            return cntn
        }else{
            return  cntu
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        //var cell:AnyClass
        //var cellx:UICollectionViewCell
        
        if collectionView.tag == 1 {
            let cell = collectionViewPopular.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath as IndexPath) as! HomeCell
            
            cell.cardView?.imageShow.frame = CGRect(x: 0, y: 0,width: (cell.cardView?.frame.width)!, height: (cell.cardView?.frame.height)!-35)
            cell.cardView?.title.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-30,width: (cell.cardView?.frame.width)!, height: 20)
            cell.cardView?.genre.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-15,width: (cell.cardView?.frame.width)!, height: 20)
            
            let key = self.dataPopular?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                cell.cardView?.imageShow.imageFromUrl(urlString: "\(URL_IMAGE)\(dictionary["poster_path"] as! String)")
                cell.cardView?.title.text = dictionary["original_title"] as! String
                cell.cardView?.genre.text = dictionary["original_title"] as! String
            }
            
            return cell
        }else if collectionView.tag == 2 {
            let cell = collectionViewNowplaying.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath as IndexPath) as! HomeCell
            
            cell.cardView?.imageShow.frame = CGRect(x: 0, y: 0,width: (cell.cardView?.frame.width)!, height: (cell.cardView?.frame.height)!-35)
            cell.cardView?.title.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-30,width: (cell.cardView?.frame.width)!, height: 20)
            cell.cardView?.genre.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-15,width: (cell.cardView?.frame.width)!, height: 20)
            
            let key = self.dataNowplaying?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                cell.cardView?.imageShow.imageFromUrl(urlString: "\(URL_IMAGE)\(dictionary["poster_path"] as! String)")
                cell.cardView?.title.text = dictionary["original_title"] as! String
                cell.cardView?.genre.text = dictionary["original_title"] as! String
            }
            
            return cell
        }else{
            let cell = collectionViewUpcoming.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath as IndexPath) as! HomeCell
            
            cell.cardView?.imageShow.frame = CGRect(x: 0, y: 0,width: (cell.cardView?.frame.width)!, height: (cell.cardView?.frame.height)!-35)
            cell.cardView?.title.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-30,width: (cell.cardView?.frame.width)!, height: 20)
            cell.cardView?.genre.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-15,width: (cell.cardView?.frame.width)!, height: 20)
            
            let key = self.dataUpcoming?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                cell.cardView?.imageShow.imageFromUrl(urlString: "\(URL_IMAGE)\(dictionary["poster_path"] as! String)")
                cell.cardView?.title.text = dictionary["original_title"] as! String
                cell.cardView?.genre.text = dictionary["original_title"] as! String
            }
            return cell
        }
        
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        var itemsCount : CGFloat?
        var heighval : CGSize?
        
        if collectionView.tag == 1 {
            itemsCount = 1.0
            if UIApplication.shared.statusBarOrientation != UIInterfaceOrientation.portrait {
                itemsCount = 1.0
            }
            heighval = CGSize(width: (self.view.frame.size.width/itemsCount!) * 1/3, height: collectionViewPopular.frame.height/itemsCount!);
        }
        
        if collectionView.tag == 2 {
            itemsCount = 1.0
            if UIApplication.shared.statusBarOrientation != UIInterfaceOrientation.portrait {
                itemsCount = 1.0
            }
            heighval = CGSize(width: (self.view.frame.size.width/itemsCount!) * 1/3, height: collectionViewNowplaying.frame.height/itemsCount!);
        }
        
        if collectionView.tag == 3 {
            itemsCount = 1.0
            if UIApplication.shared.statusBarOrientation != UIInterfaceOrientation.portrait {
                itemsCount = 1.0
            }
            heighval = CGSize(width: (self.view.frame.size.width/itemsCount!) * 1/3, height: collectionViewUpcoming.frame.height/itemsCount!);
        }

        return heighval!
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath){
        var idmoview = Int()
        if collectionView.tag == 1 {
            let key = self.dataPopular?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                idmoview = dictionary["id"] as! Int
            }
        }else if collectionView.tag == 2 {
            let key = self.dataNowplaying?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                idmoview = dictionary["id"] as! Int
            }
        }else{
            let key = self.dataUpcoming?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                idmoview = dictionary["id"] as! Int
            }
        }
        
                let titleDict: NSDictionary = [NSAttributedStringKey.foregroundColor: UIColor.white]
                let closeButton : UIBarButtonItem;
                closeButton = UIBarButtonItem(title: "< Back", style: UIBarButtonItemStyle.done, target: self, action: #selector(self.back))
                closeButton.tintColor = UIColor.red
                closeButton.setTitleTextAttributes(titleDict as! [NSAttributedStringKey : Any], for: .normal)
                
                let storyBoard : UIStoryboard = UIStoryboard(name: "Main", bundle:nil)
                let nextViewController = storyBoard.instantiateViewController(withIdentifier: "detailmovie") as! DetailMovieViewController
                
                let nav : UINavigationController = UINavigationController(rootViewController: nextViewController)
                nextViewController.navigationController?.navigationBar.isTranslucent = true
                nextViewController.idmovie = idmoview
                nav.navigationBar.titleTextAttributes = titleDict as! [NSAttributedStringKey : Any]
                nav.navigationBar.barTintColor = UIColor.white
                nextViewController.navigationController?.navigationBar.isTranslucent = true
                nextViewController.navigationItem.setLeftBarButton(closeButton, animated: true)
                self.present(nav, animated:true, completion:nil)
        
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func navSetting(){
        self.navigationItem.title = "Movie App"
        self.navigationController?.navigationBar.titleTextAttributes = [
            NSAttributedStringKey.foregroundColor : UIColor.white
        ]
        //self.navigationController?.navigationBar.barTintColor = UIColor.white
        self.navigationController?.navigationBar.setBackgroundImage(UIImage(), for: .default)
        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.isTranslucent = true
        self.navigationController?.view.backgroundColor = .clear
        self.view.backgroundColor = UIColor.init(hexString: "#6f5df3")
    }
    
    func layout(){
        scroll.frame = CGRect(x: 0, y: 70, width: view.bounds.size.width, height: view.bounds.size.height)
        scroll.backgroundColor = UIColor.clear
        scroll.contentSize = CGSize(width: screenSize.size.width, height: screenSize.size.height+100);
        scroll.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        scroll.delegate = self
        scroll.isScrollEnabled = true
        
        txtPopular.frame = CGRect(x: 20, y: 0, width: screenSize.size.width-120, height: 30)
        txtPopular.text = "Popular Movies"
        txtPopular.textColor = UIColor.white
        scroll.addSubview(txtPopular)
        
        btnPopular.frame = CGRect(x: screenSize.size.width-100, y: 0, width: 100, height: 30)
        btnPopular.setTitle("more",for: .normal)
        btnPopular.tag = 1
        btnPopular.setTitleColor(UIColor.white, for: .normal)
        btnPopular.addTarget(self, action: #selector(self.detailist(sender:)), for: UIControlEvents.touchUpInside)
        scroll.addSubview(btnPopular)
        
        scroll.addSubview(self.collectionViewPopular)
        
        txtNowplaying.frame = CGRect(x: 20, y: collectionViewPopular.frame.origin.y + collectionViewPopular.frame.size.height + 10, width: screenSize.size.width-120, height: 30)
        txtNowplaying.text = "Now Playing"
        txtNowplaying.textColor = UIColor.white
        scroll.addSubview(txtNowplaying)
        
        btnNowplaying.frame = CGRect(x: screenSize.size.width-100, y: collectionViewPopular.frame.origin.y + collectionViewPopular.frame.size.height + 10, width: 100, height: 30)
        btnNowplaying.setTitle("more",for: .normal)
        btnNowplaying.tag = 2
        btnNowplaying.setTitleColor(UIColor.white, for: .normal)
        btnNowplaying.addTarget(self, action: #selector(self.detailist(sender:)), for: UIControlEvents.touchUpInside)
        scroll.addSubview(btnNowplaying)
        
        scroll.addSubview(self.collectionViewNowplaying)
        
        txtUpcoming.frame = CGRect(x: 20, y: collectionViewNowplaying.frame.origin.y + collectionViewNowplaying.frame.size.height + 10, width: screenSize.size.width-120, height: 30)
        txtUpcoming.text = "Upcoming Movies"
        txtUpcoming.textColor = UIColor.white
        scroll.addSubview(txtUpcoming)
        
        btnUpcoming.frame = CGRect(x: screenSize.size.width-100, y: collectionViewNowplaying.frame.origin.y + collectionViewNowplaying.frame.size.height + 10, width: 100, height: 30)
        btnUpcoming.setTitle("more",for: .normal)
        btnUpcoming.tag = 3
        btnUpcoming.setTitleColor(UIColor.white, for: .normal)
        btnUpcoming.addTarget(self, action: #selector(self.detailist(sender:)), for: UIControlEvents.touchUpInside)
        scroll.addSubview(btnUpcoming)
        
        scroll.addSubview(self.collectionViewUpcoming)
        
        self.view.addSubview(scroll)
    }
    
    func getDataToken(){
        let parameters = ["api_key"  : USER_apikey]
        Alamofire.request(URL_TOKEN, method: .get, parameters: parameters).responseJSON(completionHandler: { (response) in
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                if JSONDict.value(forKey: "success") as! Bool == true {
                    self.userDefaults.set(JSONDict.value(forKey: "expires_at") as! String, forKey: "expires_at")
                    self.userDefaults.set(JSONDict.value(forKey: "request_token") as! String, forKey: "request_token")
                    self.userDefaults.synchronize()
                    //print("data token \(self.userDefaults.value(forKey: "request_token"))")
                    
                    let parameterlogin = ["api_key"  : USER_apikey,"username" : USER_username,"password" : USER_password,"request_token": self.userDefaults.value(forKey: "request_token") as! String]
                    Alamofire.request(URL_LOGIN, method: .get, parameters: parameterlogin).responseJSON(completionHandler: { (response) in
                        if response.result.isSuccess {
                            let parametersession = ["api_key"  : USER_apikey,"request_token": self.userDefaults.value(forKey: "request_token") as! String]
                            Alamofire.request(URL_SESSION, method: .get, parameters: parametersession).responseJSON(completionHandler: { (response) in
                                
                                if response.result.isSuccess {
                                    print("data login \(response.result.value!)")
                                    self.getPopular()
                                    self.getNowplaying()
                                    self.getUpcoming()
                                }
                            })
                        }
                    })
                }
            }
            
        })
    }
    
    @objc func detailist(sender:UIButton){
        let titleDict: NSDictionary = [NSAttributedStringKey.foregroundColor: UIColor.white]
        let closeButton : UIBarButtonItem;
        closeButton = UIBarButtonItem(title: "< Back", style: UIBarButtonItemStyle.done, target: self, action: #selector(self.back))
        closeButton.tintColor = UIColor.red
        closeButton.setTitleTextAttributes(titleDict as! [NSAttributedStringKey : Any], for: .normal)
        
        let storyBoard : UIStoryboard = UIStoryboard(name: "Main", bundle:nil)
        let nextViewController = storyBoard.instantiateViewController(withIdentifier: "detaillist") as! DetailListViewController
        
        let nav : UINavigationController = UINavigationController(rootViewController: nextViewController)
        //let attribute = [NSAttributedStringKey.foregroundColor : UIColor.red]
        //nextViewController.navigationController?.navigationBar.titleTextAttributes = titleDict as! [NSAttributedStringKey : Any]
        nextViewController.navigationController?.navigationBar.isTranslucent = true
        nextViewController.tag = sender.tag
        if sender.tag == 1 {
            nextViewController.title = "Popular Movies"
        }else if sender.tag == 2 {
            nextViewController.title = "Now Playing"
        }else if sender.tag == 3 {
            nextViewController.title = "Upcoming Movies"
        }
        nav.navigationBar.titleTextAttributes = titleDict as! [NSAttributedStringKey : Any]
        nav.navigationBar.barTintColor = UIColor.white
        nextViewController.navigationController?.navigationBar.isTranslucent = true
        nextViewController.navigationItem.setLeftBarButton(closeButton, animated: true)
        self.present(nav, animated:true, completion:nil)
        
        //nextViewController.navigationController?.navigationBar.isTranslucent = true
//        nextViewController.navigationItem.setLeftBarButton(closeButton, animated: true)
//        self.dismiss(animated: true, completion: nil)
//        self.present(nextViewController, animated: true, completion: nil)
    }
    
    @objc func back() {
        let transition = CATransition()
        transition.duration = 0.3
        transition.type = kCATransitionReveal
        transition.subtype = kCATransitionFromLeft
        //self.navigationController!.view.layer.addAnimation(transition, forKey: kCATransition)
        //self.navigationController!.popViewControllerAnimated(true)
        self.dismiss(animated: true, completion: nil)
    }
    
    func getPopular(){
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US","page" : "1"]
        Alamofire.request(URL_POPULAR, method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                print("DATA POPULAR \(JSONDict)")
                if let dictionary = response.result.value as? [String : AnyObject]{
                    self.dataPopular = dictionary["results"] as! [[String : AnyObject]] as NSArray
                    self.cntp = (self.dataPopular?.count) as! Int
                    self.collectionViewPopular.reloadData()
                }
            }
        })
    }
    
    func getNowplaying(){
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US","page" : "1"]
        Alamofire.request(URL_NOWPLAYING, method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                if let dictionary = response.result.value as? [String : AnyObject]{
                    self.dataNowplaying = dictionary["results"] as! [[String : AnyObject]] as NSArray
                    self.cntn = (self.dataNowplaying?.count) as! Int
                    self.collectionViewNowplaying.reloadData()
                }
            }
        })
    }
    
    func getUpcoming(){
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US","page" : "1"]
        Alamofire.request(URL_UPCOMING, method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                if let dictionary = response.result.value as? [String : AnyObject]{
                    self.dataUpcoming = dictionary["results"] as! [[String : AnyObject]] as NSArray
                    self.cntu = (self.dataUpcoming?.count) as! Int
                    self.collectionViewUpcoming.reloadData()
                }
            }
        })
    }
}

extension UIColor {
    convenience init(hexString: String, alpha: CGFloat = 1.0) {
        let hexString: String = hexString.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines)
        let scanner = Scanner(string: hexString)
        if (hexString.hasPrefix("#")) {
            scanner.scanLocation = 1
        }
        var color: UInt32 = 0
        scanner.scanHexInt32(&color)
        let mask = 0x000000FF
        let r = Int(color >> 16) & mask
        let g = Int(color >> 8) & mask
        let b = Int(color) & mask
        let red   = CGFloat(r) / 255.0
        let green = CGFloat(g) / 255.0
        let blue  = CGFloat(b) / 255.0
        self.init(red:red, green:green, blue:blue, alpha:alpha)
    }
    
    func toHexString() -> String {
        var r:CGFloat = 0
        var g:CGFloat = 0
        var b:CGFloat = 0
        var a:CGFloat = 0
        getRed(&r, green: &g, blue: &b, alpha: &a)
        let rgb:Int = (Int)(r*255)<<16 | (Int)(g*255)<<8 | (Int)(b*255)<<0
        return String(format:"#%06x", rgb)
    }
    
    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")
        
        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }
    
    convenience init(netHex:Int) {
        self.init(red:(netHex >> 16) & 0xff, green:(netHex >> 8) & 0xff, blue:netHex & 0xff)
    }
    
    convenience init(r: Int, g: Int, b: Int) {
        self.init(red: CGFloat(r)/255, green: CGFloat(g)/255, blue: CGFloat(b)/255, alpha: 1.0)
    }
    
}

extension UIImageView {
    public func imageFromUrl(urlString: String) {
        if let url = URL.init(string: urlString) {
            let request = URLRequest.init(url: url)
            NSURLConnection.sendAsynchronousRequest(request, queue: OperationQueue.main, completionHandler: { (response, data, error) in
                if let imageData = data as NSData? {
                    self.image = UIImage(data: imageData as Data)
                }
            })
        }
    }
}

