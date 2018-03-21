//
//  DetailListViewController.swift
//  MovieApp
//
//  Created by Faisal Riza Rakhmat on 19/03/18.
//  Copyright © 2018 Faisal Riza Rakhmat. All rights reserved.
//

import UIKit
import Alamofire

class DetailListViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout, UICollectionViewDataSource,UIScrollViewDelegate {

    var screenSize = UIScreen.main.bounds
    var userDefaults = UserDefaults.standard
    var data: NSArray?
    var cntp = Int()
    var tag = Int()
    var scroll = UIScrollView()
    
    lazy var collectionView:UICollectionView = {
        var cv = UICollectionView(frame: CGRect(x: 0, y: 0, width: self.view.bounds.width, height: self.view.bounds.height), collectionViewLayout: self.flowLayout)
        cv.delegate = self
        cv.dataSource = self
        cv.bounces = false
        cv.autoresizingMask = [UIViewAutoresizing.flexibleHeight, UIViewAutoresizing.flexibleWidth]
        cv.register(HomeCell.self, forCellWithReuseIdentifier: "cell")
        cv.register(UICollectionReusableView.self, forSupplementaryViewOfKind: UICollectionElementKindSectionHeader, withReuseIdentifier: "header");
        cv.backgroundColor = UIColor.clear
        cv.isPagingEnabled = true
        cv.showsHorizontalScrollIndicator = false
        cv.showsVerticalScrollIndicator = false
        cv.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        cv.tag =  1
        return cv
    }()
    
    lazy var flowLayout:UICollectionViewFlowLayout = {
        var flow = UICollectionViewFlowLayout()
        flow.sectionInset = UIEdgeInsets(top: 5, left: 1, bottom: 5, right: 0)
        flow.minimumInteritemSpacing = 0
        flow.minimumLineSpacing = 10
        flow.scrollDirection = .vertical
        return flow
    }()
    
    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        //self.automaticallyAdjustsScrollViewInsets = false
        //self.edgesForExtendedLayout = []
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        print("tagnya \(tag)")
        self.navSetting()
        self.layout()
        self.getData()
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        //if collectionView.tag == 1 {
            return cntp
        //}
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        //var cell:AnyClass
        //var cellx:UICollectionViewCell
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath as IndexPath) as! HomeCell
        
        cell.cardView?.imageShow.frame = CGRect(x: 0, y: 0,width: (cell.cardView?.frame.width)!, height: (cell.cardView?.frame.height)!-35)
        cell.cardView?.title.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-30,width: (cell.cardView?.frame.width)!, height: 20)
        cell.cardView?.genre.frame     = CGRect(x: 0, y: (cell.cardView?.frame.height)!-15,width: (cell.cardView?.frame.width)!, height: 20)
        
        //if collectionView.tag == 1 {
            
            let key = self.data?.object(at: indexPath.row)
            if let dictionary = key as? [String: AnyObject] {
                cell.cardView?.imageShow.imageFromUrl(urlString: "\(URL_IMAGE)\(dictionary["poster_path"] as! String)")
                cell.cardView?.title.text = dictionary["original_title"] as! String
                cell.cardView?.genre.text = dictionary["original_title"] as! String
            }
            
            return cell
        //}
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
//        let SIZE = UIScreen.main.bounds.size
//        let BASE_WIDTH = (SIZE.width-10)
//        let returnX = CGSize(width: (BASE_WIDTH/2)-5, height: (SIZE.height/2)-15)
//        return returnX
        var itemsCount : CGFloat?
        var heighval : CGSize?

        if collectionView.tag == 1 {
            itemsCount = 2.0
            if UIApplication.shared.statusBarOrientation != UIInterfaceOrientation.portrait {
                itemsCount = 2.0
            }
//            heighval = CGSize(width: (self.view.frame.size.width/itemsCount!) * 1/3, height: collectionView.frame.height/itemsCount!);
            heighval = CGSize(width: UIScreen.main.bounds.size.width/itemsCount! - 10, height: UIScreen.main.bounds.size.height/itemsCount! - 10);
        }

        return heighval!
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath){
        
        let key = self.data?.object(at: indexPath.row)
        if let dictionary = key as? [String: AnyObject] {
            print("id movie \(dictionary["id"] as! Int), ")
            
            let titleDict: NSDictionary = [NSAttributedStringKey.foregroundColor: UIColor.white]
            let closeButton : UIBarButtonItem;
            closeButton = UIBarButtonItem(title: "< Back", style: UIBarButtonItemStyle.done, target: self, action: #selector(self.back))
            closeButton.tintColor = UIColor.red
            closeButton.setTitleTextAttributes(titleDict as! [NSAttributedStringKey : Any], for: .normal)
            
            let storyBoard : UIStoryboard = UIStoryboard(name: "Main", bundle:nil)
            let nextViewController = storyBoard.instantiateViewController(withIdentifier: "detailmovie") as! DetailMovieViewController
            
            let nav : UINavigationController = UINavigationController(rootViewController: nextViewController)
            nextViewController.navigationController?.navigationBar.isTranslucent = true
            nextViewController.idmovie = dictionary["id"] as! Int
            nav.navigationBar.titleTextAttributes = titleDict as! [NSAttributedStringKey : Any]
            nav.navigationBar.barTintColor = UIColor.white
            nextViewController.navigationController?.navigationBar.isTranslucent = true
            nextViewController.navigationItem.setLeftBarButton(closeButton, animated: true)
            self.present(nav, animated:true, completion:nil)
        }
        
    }
    
    @objc func back() {
        let transition = CATransition()
        transition.duration = 0.3
        transition.type = kCATransitionReveal
        transition.subtype = kCATransitionFromLeft
        self.dismiss(animated: true, completion: nil)
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
        scroll.addSubview(self.collectionView)
        
        self.view.addSubview(scroll)
    }
    
    func getData(){
        var url=String()
        if tag == 1 {
           url = URL_POPULAR
        }else if tag == 2 {
           url = URL_NOWPLAYING
        }else if tag == 3 {
            url = URL_UPCOMING
        }
        let parameter = ["api_key"  : USER_apikey,"language" : "en-US","page" : "1"]
        Alamofire.request(url, method: .get, parameters: parameter).responseJSON(completionHandler: { (response) in
            
            if response.result.isSuccess {
                let JSONDict = response.result.value as! NSDictionary
                if let dictionary = response.result.value as? [String : AnyObject]{
                    self.data = dictionary["results"] as! [[String : AnyObject]] as NSArray
                    print("data moview \(self.data)")
                    self.cntp = (self.data?.count) as! Int
                    self.collectionView.reloadData()
                }
            }
        })
    }
    
}
