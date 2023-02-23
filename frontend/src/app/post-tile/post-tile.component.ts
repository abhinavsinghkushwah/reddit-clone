import { Component } from '@angular/core';
import { PostService } from '../shared/post.service';
import { faArrowUp, faArrowDown, faComments } from '@fortawesome/free-solid-svg-icons';
import { PostModel } from '../shared/PostModel';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-tile',
  templateUrl: './post-tile.component.html',
  styleUrls: ['./post-tile.component.css']
})
export class PostTileComponent {
  
  faComments= faComments;
  posts: Array<PostModel> = [];

  constructor(private postService: PostService, private router: Router) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts = post;
    })
  }
  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}