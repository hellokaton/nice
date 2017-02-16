$(document).ready(function () {

    $('#search-inp').keypress(function (e) {
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            var q = $(this).val();
            if(q && q != ''){
                $(this).val('');
                window.open('/search?q=' + q);
            }
        }
    });

    /**
     * 注册
     */
    $('header .reg-btn').click(function () {
        var r = Math.floor(Math.random()*24)+1;
        var img_path = 'avatar/random/' + r + '.png';
        $('div#reg-modal').addClass('active');
        $('div#reg-modal #upload_img').attr('src', '/static/img/' + img_path);
        $('div#reg-modal #user_avatar').val(img_path);
        $('#reg-form').validator().on('submit', function (e) {
            stopDefault(e);
            $.ajax({
                type: "POST",
                url: '/signup',
                data: $('#reg-form').serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result && result.success) {
                        alert('注册成功，系统向你的邮箱发了一封激活邮件，请注意查收！');
                        $('#reg-form')[0].reset;
                        $('#reg-modal').removeClass('active');
                    } else {
                        if (result.msg) {
                            alert(result.msg);
                        } else {
                            alert('注册失败');
                        }
                    }
                }
            });
        });
    });

    /**
     * 登录
     */
    $('header .signin-btn').click(function () {
        $('div#signin-modal').addClass('active');
        $('#signin-form').validator().on('submit', function (e) {
            stopDefault(e);
            $.ajax({
                type: "POST",
                url: '/signin',
                data: $('#signin-form').serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result && result.success) {
                        $('#signin-form')[0].reset;
                        window.location.reload();
                    } else {
                        if (result.msg) {
                            alert(result.msg);
                        } else {
                            alert('登录失败');
                        }
                    }
                }
            });
        });
    });

    $('a,button[aria-label=close]').click(function () {
        $('div#reg-modal').removeClass('active');
        $('div#signin-modal').removeClass('active');
    });

    /**
     * 发表评论
     */
    $(document).on('click','button.comment-btn',function(){
        var id = $(this).attr('tid');
        var inputEl = $(this).parent().prev().find('input:eq(0)');
        var comment = inputEl.val();
        if (comment && comment != '') {
            $.ajax({
                type: "POST",
                url: '/comment/' + id,
                data: {comment: comment},
                dataType: 'json',
                success: function (result) {
                    if (result && result.success) {
                        alert('评论成功');
                        inputEl.val('');
                    } else {
                        if (result.msg) {
                            alert(result.msg);
                        } else {
                            alert('评论失败');
                        }
                    }
                }
            });
        }
    });

    /**
     * 点赞
     */
    $(document).on('click','.topic-star',function(){
        var tid = $(this).parents('.home-topic').attr('tid');
        var this_ = $(this);
        $.ajax({
            type: "POST",
            url: '/star/' + tid,
            dataType: 'json',
            success: function (result) {
                if (result && result.success) {
                    this_.parent().find('.topic-unstar').removeClass('hide');
                    this_.addClass('hide');
                } else {
                    if (result.msg) {
                        alert(result.msg);
                    } else {
                        alert('赞失败');
                    }
                }
            }
        });
    });

    /**
     * 取消赞
     */
    $(document).on('click','.topic-unstar',function(){
        var tid = $(this).parents('.home-topic').attr('tid');
        var this_ = $(this);
        $.ajax({
            type: "POST",
            url: '/unstar/' + tid,
            dataType: 'json',
            success: function (result) {
                if (result && result.success) {
                    this_.parent().find('.topic-star').removeClass('hide');
                    this_.addClass('hide');
                } else {
                    if (result.msg) {
                        alert(result.msg);
                    } else {
                        alert('赞失败');
                    }
                }
            }
        });
    });

    /**
     * 首页加载更多
     */
    $(document).on('click','button.home-load-more',function(){
        var this_ = $(this);
        this_.addClass('loading');
        var page = parseInt(this_.attr('page'));
        $.getJSON('/topics/0/' + page, {}, function (result) {
            if (result && result.success) {
                setTimeout(function () {
                    this_.removeClass('loading');
                    var list = result.payload;
                    if (list.length > 0) {
                        var tpl = document.getElementById('home-topic-tpl').innerHTML.trim();
                        var data = {
                            topics: list,
                            published: function () {
                                return new Date().ago(this.created * 1000);
                            },
                            loginuser: LOGIN_USER,
                            img_url: IMG_URL
                        };
                        var html = Mustache.render(tpl, data);
                        this_.parent().before(html);
                        this_.attr('page', page + 1);
                    } else {
                        this_.text('没有更多内容了。');
                    }
                }, 500);
            }
        });
    });

    /**
     * 个人主页动态加载
     */
    $(document).on('click','button.user-load-more',function(){
        var this_ = $(this);
        this_.addClass('loading');
        var page = parseInt(this_.attr('page'));
        $.getJSON('/topics/1/' + page, {}, function (result) {
            if (result && result.success) {
                setTimeout(function () {
                    this_.removeClass('loading');
                    var list = result.payload;
                    if (list.length > 0) {
                        var tpl = document.getElementById('user-topic-tpl').innerHTML.trim();
                        var data = {
                            topics: list,
                            published: function () {
                                return new Date().ago(this.created * 1000);
                            },
                            loginuser: LOGIN_USER,
                            img_url: IMG_URL
                        };
                        var html = Mustache.render(tpl, data);
                        this_.parent().before(html);
                        this_.attr('page', page + 1);
                    } else {
                        this_.text('没有更多内容了。');
                    }
                }, 500);
            }
        });
    });

    $(".fileUpload").liteUploader({
        script: "/upload",
        params: {type: 'avatar'},
        rules: {
            allowedFileTypes: "image/jpeg,image/png,image/jpeg",
            maxSize: 512000
        }
    }).on("lu:success", function (e, data) {
        if (data) {
            $("#upload_img").attr('src', data.url);
            $("#user_avatar").val(data.savekey);
        }
    }).on("lu:errors", function (e, data) {
        console.log(data);
        if (data && data.length > 0) {
            var item = data[0];
            if (item && item.errors && item.errors.length > 0) {
                var err = item.errors[0];
                if (err.type == 'size') {
                    alert("图片大小超出限制,请重新上传");
                }
            }
        }
    });

    $(".fileUpload").change(function () {
        $(this).data("liteUploader").startUpload();
    });
});


//上传方法
function doUpload() {
    $(".fileUpload").click();
}