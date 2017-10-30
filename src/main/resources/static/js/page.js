//page
var cur_page=1;
var total_page;
var total_record;
var show_page=5;
var flag_page=1;
function page_forward(index){
    cur_page=index;
    flag_page=cur_page;
    param.start=(index-1)*10;
    load_item();
}
function page_previous(){
    flag_page=max(flag_page-5,1);
    calc_page();
}
function page_next(){
    flag_page=min(flag_page+5,total_page);
    calc_page();
}
function calc_page(){
    var ul=$("#page_ul");
    ul.empty();
    var left,right;
    total_page=Div(total_record,param.limit)+1;
    if(flag_page-2<1){
        left=1;
        right=min(2-(flag_page-left)+2+flag_page,total_page);
    }
    else if(flag_page+2>total_page){
        right=total_page;
        left=max(flag_page-(4-(right-flag_page)),1);
    }
    else{
        left=flag_page-2;
        right=flag_page+2;
    }
    if(left==1){
        ul.append("<li class='disabled'><a href='javascript:page_previous()' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
    }
    else{
        ul.append("<li><a href='javascript:page_previous()' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li>");
    }
    for(var i=left;i<=right;i++){
        if(i==cur_page){
            ul.append("<li class='active'><a href='javascript:page_forward("+i+")'>"+i+"</a></li>");
        }
        else{
            ul.append("<li><a href='javascript:page_forward("+i+")'>"+i+"</a></li>");
        }
    }
    if(right==total_page){
        ul.append("<li class='disabled'><a href='javascript:page_next()' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
    }
    else{
        ul.append("<li><a href='javascript:page_next()' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li>");
    }
}
function min(a,b){
    if(a>b)
        return b;
    else
        return a;
}
function max(a,b){
    if(a>b)
        return a;
    else
        return b;
}
function Div(exp1, exp2) {
    var n1 = Math.round(exp1); //四舍五入
    var n2 = Math.round(exp2); //四舍五入

    var rslt = n1 / n2; //除

    if (rslt >= 0) {
        rslt = Math.floor(rslt); //返回小于等于原rslt的最大整数。
    }
    else {
        rslt = Math.ceil(rslt); //返回大于等于原rslt的最小整数。
    }

    return rslt;
}