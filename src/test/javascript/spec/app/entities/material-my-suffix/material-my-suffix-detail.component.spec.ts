/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FrTestModule } from '../../../test.module';
import { MaterialMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix-detail.component';
import { MaterialMySuffixService } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix.service';
import { MaterialMySuffix } from '../../../../../../main/webapp/app/entities/material-my-suffix/material-my-suffix.model';

describe('Component Tests', () => {

    describe('MaterialMySuffix Management Detail Component', () => {
        let comp: MaterialMySuffixDetailComponent;
        let fixture: ComponentFixture<MaterialMySuffixDetailComponent>;
        let service: MaterialMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [MaterialMySuffixDetailComponent],
                providers: [
                    MaterialMySuffixService
                ]
            })
            .overrideTemplate(MaterialMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MaterialMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MaterialMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MaterialMySuffix(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.material).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
